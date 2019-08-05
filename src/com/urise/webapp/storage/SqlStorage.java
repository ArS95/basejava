package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
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
    private SqlHelper sqlHelper;
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

            try (PreparedStatement st = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                st.setString(1, uuid);
                st.executeUpdate();
            }

            insertContact(conn, resume);
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
            return null;
        });
    }

    private void insertContact(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO contact (type, value, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getAllContacts().entrySet()) {
                setString(st, e.getKey().name(), e.getValue());
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

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.<Void>execute(st -> {
            st.setString(1, uuid);
            executeOrNotExist(st, uuid);
            return null;
        }, "DELETE FROM resume WHERE uuid = ?");
    }

    private void executeOrNotExist(PreparedStatement preparedStatement, String uuid) throws SQLException {
        if (preparedStatement.executeUpdate() != 1) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.execute(st -> {
            st.setString(1, uuid);
            ResultSet resultSet = st.executeQuery();
            if (!resultSet.next()) {
                LOG.warning("Resume " + uuid + " not exist");
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, resultSet.getString("full_name"));
            do {
                final String type = resultSet.getString("type");
                if (type != null) {
                    resume.addContact(ContactType.valueOf(type), resultSet.getString("value"));
                }
            }
            while (resultSet.next());
            return resume;
        }, "SELECT * FROM resume r" +
                " LEFT JOIN contact c " +
                "    ON r.uuid = c.resume_uuid" +
                "  WHERE r.uuid = ?");
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted ");
        return sqlHelper.transactionExecute(conn -> {
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            executeSqlAction(conn, "SELECT * FROM resume ORDER BY full_name, uuid", set -> {
                resumeMap.put(set.getString("uuid"), new Resume(set.getString("uuid"), set.getString("full_name")));
            });
            executeSqlAction(conn, "SELECT * FROM contact", set -> {
                Resume resume = resumeMap.get(set.getString("resume_uuid"));
                resume.addContact(ContactType.valueOf(set.getString("type")), set.getString("value"));
            });
            return new ArrayList<>(resumeMap.values());
        });
    }

    private void executeSqlAction(Connection connection, String sql, ResultSetAction element) throws SQLException {
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                element.action(resultSet);
            }
        }
    }

    @Override
    public int size() {
        LOG.info("Size");
        return sqlHelper.execute(st -> {
            int size = 0;
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                size = resultSet.getInt("count");
            }
            return size;
        }, "SELECT COUNT(*) FROM resume");
    }
}

interface ResultSetAction {
    void action(ResultSet set) throws SQLException;
}

