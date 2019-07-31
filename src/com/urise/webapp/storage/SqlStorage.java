package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.util.helper.SqlHelper;
import com.urise.webapp.util.helper.StatementExecutor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    protected SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        StatementExecutor<Void> stExecutor = (st) -> {
            st.executeUpdate();
            return null;
        };
        execute(stExecutor, "DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        StatementExecutor<Void> stExecutor = (st) -> {
            final String uuid = resume.getUuid();
            setString(st, resume.getFullName(), uuid);
            if (st.executeUpdate() != 1) {
                LOG.warning("Resume " + uuid + " not exist");
                throw new NotExistStorageException(uuid);
            }
            return null;
        };
        execute(stExecutor, "UPDATE resume SET full_name = ? WHERE uuid = ?");
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        StatementExecutor<Void> stExecutor = (st) -> {
            final String uuid = resume.getUuid();
            setString(st, uuid, resume.getFullName());
            try {
                st.executeUpdate();
            } catch (SQLException e) {
                LOG.warning("Resume " + uuid + " already exist");
                if (e.getSQLState().equals("23505")) {
                    throw new ExistStorageException(e.getMessage(), uuid, e);
                } else {
                    throw new StorageException(e.getMessage(), uuid, e);
                }
            }
            return null;
        };
        execute(stExecutor, "INSERT INTO resume (uuid, full_name) VALUES (?,?)");
    }

    private void setString(PreparedStatement statement, String uuid, String full_name) throws SQLException {
        statement.setString(1, uuid);
        statement.setString(2, full_name);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        StatementExecutor<Resume> stExecutor = (st) -> {
            st.setString(1, uuid);
            ResultSet resultSet = st.executeQuery();
            if (!resultSet.next()) {
                LOG.warning("Resume " + uuid + " not exist");
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        };
        return execute(stExecutor, "SELECT * FROM resume r WHERE r.uuid = ?");
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        StatementExecutor<Void> stExecutor = (st) -> {
            st.setString(1, uuid);
            if (st.executeUpdate() != 1) {
                LOG.warning("Resume " + uuid + " not exist");
                throw new NotExistStorageException(uuid);
            }
            return null;
        };
        execute(stExecutor, "DELETE FROM resume WHERE uuid = ?");
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted ");
        StatementExecutor<List<Resume>> stExecutor = (st) -> {
            List<Resume> sortedList = new ArrayList<>();
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                sortedList.add(new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name")));
            }
            return sortedList;
        };
        return execute(stExecutor, "SELECT * FROM resume ORDER BY uuid, full_name");
    }

    @Override
    public int size() {
        LOG.info("Size");
        StatementExecutor<Integer> stExecutor = (st) -> {
            int size = 0;
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                size =  resultSet.getInt("count");
            }
            return size;
        };
        return execute(stExecutor, "SELECT COUNT(*) AS count FROM resume");
    }

    private <T> T execute(StatementExecutor<T> statement, String sql) {
        return sqlHelper.execute(statement, sql);
    }
}

