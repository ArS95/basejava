package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.sql.helper.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        sqlHelper.<Void>execute(st -> {
            st.executeUpdate();
            return null;
        }, "DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        sqlHelper.<Void>transactionExecute(conn -> {
            final String uuid = resume.getUuid();
            try (PreparedStatement st = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                setString(st, resume.getFullName(), uuid);
                executeOrNotExist(st, uuid);
            }

            deleteContactAndSection(conn, "DELETE FROM contact WHERE resume_uuid = ?", uuid);
            insertContacts(conn, resume);
            deleteContactAndSection(conn, "DELETE FROM section WHERE resume_uuid = ?", uuid);
            insertSections(conn, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        sqlHelper.<Void>transactionExecute(conn -> {
            final String uuid = resume.getUuid();
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO resume (full_name,uuid) VALUES (?,?)")) {
                setString(st, resume.getFullName(), uuid);
                st.execute();
            }

            insertContacts(conn, resume);
            insertSections(conn, resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.<Void>execute(st -> {
            st.setString(1, uuid);
            executeOrNotExist(st, uuid);
            return null;
        }, "DELETE FROM resume WHERE uuid = ?");
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.transactionExecute(conn -> {
            Resume resume;
            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
                st.setString(1, uuid);
                ResultSet resultSet = st.executeQuery();
                if (!resultSet.next()) {
                    LOG.warning("Resume " + uuid + " not exist");
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
            }

            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM contact c WHERE c.resume_uuid = ?")) {
                st.setString(1, uuid);
                ResultSet resultSet = st.executeQuery();
                while (resultSet.next()) {
                    addContact(resultSet, resume);
                }
            }

            try (PreparedStatement st = conn.prepareStatement("SELECT * FROM section s WHERE s.resume_uuid = ?")) {
                st.setString(1, uuid);
                ResultSet resultSet = st.executeQuery();
                while (resultSet.next()) {
                    addSection(resultSet, resume);
                }
            }
            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted ");
        return sqlHelper.transactionExecute(conn -> {
            final Map<String, Resume> resumeMap = new LinkedHashMap<>();

            executeSqlAction(conn, "SELECT * FROM resume ORDER BY full_name, uuid", set -> {
                resumeMap.put(set.getString("uuid"), new Resume(set.getString("uuid"), set.getString("full_name")));
            });

            executeSqlAction(conn, "SELECT * FROM contact", set -> {
                Resume resume = resumeMap.get(set.getString("resume_uuid"));
                addContact(set, resume);
            });

            executeSqlAction(conn, "SELECT * FROM section", set -> {
                Resume resume = resumeMap.get(set.getString("resume_uuid"));
                addSection(set, resume);
            });

            return new ArrayList<>(resumeMap.values());
        });
    }

    @Override
    public int size() {
        LOG.info("Size");
        return sqlHelper.execute(st -> {
            int size = 0;
            final ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                size = resultSet.getInt("count");
            }
            return size;
        }, "SELECT COUNT(*) FROM resume");
    }

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO contact (contact_type, contact_value, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getAllContacts().entrySet()) {
                setString(st, entry.getKey().name(), entry.getValue());
                st.setString(3, resume.getUuid());
                st.addBatch();
            }
            st.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO section (section_type, section_value, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getAllSections().entrySet()) {
                final SectionType key = entry.getKey();
                setString(st, key.name(), JsonParser.write(entry.getValue(), AbstractSection.class));
                st.setString(3, resume.getUuid());
                st.addBatch();
            }
            st.executeBatch();
        }
    }

    private void setString(PreparedStatement st, String firstSet, String secondSet) throws SQLException {
        st.setString(1, firstSet);
        st.setString(2, secondSet);
    }

    private void addSection(ResultSet set, Resume resume) throws SQLException {
        String sectionTypeName = set.getString("section_type");
        if (sectionTypeName != null) {
            SectionType sectionType = SectionType.valueOf(sectionTypeName);
            resume.addSection(sectionType, JsonParser.read(set.getString("section_value"), AbstractSection.class));
        }
    }

    private void addContact(ResultSet set, Resume resume) throws SQLException {
        final String contactType = set.getString("contact_type");
        if (contactType != null) {
            resume.addContact(ContactType.valueOf(contactType), set.getString("contact_value"));
        }
    }

    private void executeOrNotExist(PreparedStatement preparedStatement, String uuid) throws SQLException {
        if (preparedStatement.executeUpdate() != 1) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
    }

    private void executeSqlAction(Connection connection, String sql, ResultSetAction element) throws SQLException {
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            final ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                element.action(resultSet);
            }
        }
    }

    private void deleteContactAndSection(Connection conn, String sql, String uuid) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, uuid);
            st.executeUpdate();
        }
    }
}

interface ResultSetAction {
    void action(ResultSet set) throws SQLException;
}

