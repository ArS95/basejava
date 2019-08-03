package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.helper.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
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
        executeSaveAndUpdate(resume, "UPDATE resume SET full_name = ? WHERE uuid = ?", "UPDATE contact SET type = ?, value = ? WHERE resume_uuid = ?");
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        executeSaveAndUpdate(resume, "INSERT INTO resume (full_name,uuid) VALUES (?,?)", "INSERT INTO contact (type, value, resume_uuid) VALUES (?,?,?)");
    }

    private void executeSaveAndUpdate(Resume resume, String resumeSql, String contactSql) {
        sqlHelper.<Void>transactionExecute(conn -> {
            final String uuid = resume.getUuid();
            try (PreparedStatement st = conn.prepareStatement(resumeSql)) {
                st.setString(1, resume.getFullName());
                st.setString(2, uuid);
                executeUpdate(st, uuid);
            }
            try (PreparedStatement st = conn.prepareStatement(contactSql)) {
                for (Map.Entry<ContactType, String> e : resume.getAllContacts().entrySet()) {
                    st.setString(1, e.getKey().name());
                    st.setString(2, e.getValue());
                    st.setString(3, uuid);
                    st.addBatch();
                }
                st.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.<Void>execute(st -> {
            st.setString(1, uuid);
            executeUpdate(st, uuid);
            return null;
        }, "DELETE FROM resume WHERE uuid = ?");
    }

    private void executeUpdate(PreparedStatement preparedStatement, String uuid) throws SQLException {
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
            List<Resume> sortedList = new ArrayList<>();
            executeSqlAction(conn, "SELECT * FROM resume", set -> {
                sortedList.add(new Resume(set.getString("uuid"), set.getString("full_name")));
            });
            Map<String, EnumMap<ContactType, String>> resultMap = new HashMap<>();
            executeSqlAction(conn, "SELECT * FROM contact", set -> {
                if (resultMap.containsKey(set.getString("resume_uuid"))) {
                    putMap(set, resultMap, resultMap.get(set.getString("resume_uuid")));
                } else {
                    putMap(set, resultMap, new EnumMap<>(ContactType.class));
                }
            });
            sortedList.forEach(r -> resultMap.forEach((k, v) -> {
                if (k.equals(r.getUuid())) {
                    r.addContactList(v);
                }
            }));
            return sortedList;
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

    private void putMap(ResultSet resultSet, Map<String, EnumMap<ContactType, String>> resultMap, EnumMap<ContactType, String> contactMap) throws SQLException {
        contactMap.put(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
        resultMap.put(resultSet.getString("resume_uuid"), contactMap);
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
        }, "SELECT COUNT(*) AS count FROM resume");
    }
}

interface ResultSetAction {
    void action(ResultSet set) throws SQLException;
}

