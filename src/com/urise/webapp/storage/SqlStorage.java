package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.helper.SqlHelper;

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
            insertContact(conn, resume);
            deleteContactAndSection(conn, "DELETE FROM section WHERE resume_uuid = ?", uuid);
            insertSection(conn, resume);
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

            insertContact(conn, resume);
            insertSection(conn, resume);
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
        return sqlHelper.execute(st -> {
            st.setString(1, uuid);
            final ResultSet resultSet = st.executeQuery();
            if (!resultSet.next()) {
                LOG.warning("Resume " + uuid + " not exist");
                throw new NotExistStorageException(uuid);
            }
            final Resume resume = new Resume(uuid, resultSet.getString("full_name"));

            do {
                addContact(resultSet, resume);
                addSection(resultSet, resume);
            }
            while (resultSet.next());
            return resume;
        }, "SELECT * FROM resume r" +
                " LEFT JOIN contact c " +
                "    ON r.uuid = c.resume_uuid" +
                " LEFT JOIN section s" +
                "    ON r.uuid = s.resume_uuid" +
                "  WHERE r.uuid = ?");
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

    private void insertContact(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO contact (contact_type, contact_value, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getAllContacts().entrySet()) {
                setString(st, entry.getKey().name(), entry.getValue());
                st.setString(3, resume.getUuid());
                st.addBatch();
            }
            st.executeBatch();
        }
    }

    private void insertSection(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO section (section_type, section_value, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getAllSections().entrySet()) {
                final SectionType key = entry.getKey();
                switch (key) {
                    case PERSONAL:
                    case OBJECTIVE:
                        final SimpleTextSection simpleTextSection = (SimpleTextSection) entry.getValue();
                        setString(st, key.name(), simpleTextSection.getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        final MarkedTextSection markedTextSection = (MarkedTextSection) entry.getValue();
                        String string = String.join("\n", markedTextSection.getAllText());
                        setString(st, key.name(), string);
                        break;
                }
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
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(sectionType, new SimpleTextSection(set.getString("section_value")));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String[] strings = set.getString("section_value").split("\n");
                    resume.addSection(sectionType, new MarkedTextSection(strings));
                    break;
            }
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

