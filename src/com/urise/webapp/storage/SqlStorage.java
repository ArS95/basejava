package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.strategy.Helper.MyStatement;
import com.urise.webapp.strategy.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
        MyStatement<PreparedStatement> myStatement = (st) -> {
            st.executeUpdate();
            return st;
        };
        execute(myStatement, "DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        MyStatement<PreparedStatement> myStatement = (st) -> {
            final String uuid = resume.getUuid();
            setString(st, resume.getFullName(), uuid);
            if (st.executeUpdate() != 1) {
                LOG.warning("Resume " + uuid + " not exist");
                throw new NotExistStorageException(uuid);
            }
            return st;
        };
        execute(myStatement, "UPDATE resume SET full_name = ? WHERE uuid = ?");
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        MyStatement<PreparedStatement> myStatement = (st) -> {
            final String uuid = resume.getUuid();
            setString(st, uuid, resume.getFullName());
            try {
                st.executeUpdate();
            } catch (SQLException e) {
                LOG.warning("Resume " + uuid + " already exist");
                throw new ExistStorageException(e.getMessage(), uuid, e);
            }
            return st;
        };
        execute(myStatement, "INSERT INTO resume (uuid, full_name) VALUES (?,?)");
    }

    private void setString(PreparedStatement statement, String uuid, String full_name) throws SQLException {
        statement.setString(1, uuid);
        statement.setString(2, full_name);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        Resume[] resume = new Resume[1];
        MyStatement<PreparedStatement> myStatement = (st) -> {
            st.setString(1, uuid);
            ResultSet resultSet = st.executeQuery();
            if (!resultSet.next()) {
                LOG.warning("Resume " + uuid + " not exist");
                throw new NotExistStorageException(uuid);
            }
            resume[0] = new Resume(uuid, resultSet.getString("full_name"));
            return st;
        };
        execute(myStatement, "SELECT * FROM resume r WHERE r.uuid = ?");
        return resume[0];
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        MyStatement<PreparedStatement> myStatement = (st) -> {
            st.setString(1, uuid);
            try {
                st.executeQuery();
            } catch (SQLException e) {
                LOG.warning("Resume " + uuid + " not exist");
                throw new NotExistStorageException(e.getMessage(), uuid, e);
            }
            return st;
        };
        execute(myStatement, "DELETE FROM resume WHERE uuid = ?");
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted ");
        List<Resume> sortedList = new ArrayList<>();
        MyStatement<PreparedStatement> myStatement = (st) -> {
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                sortedList.add(new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name")));
            }
            return st;
        };
        execute(myStatement, "SELECT * FROM resume");
        Collections.sort(sortedList);
        return sortedList;
    }

    @Override
    public int size() {
        LOG.info("Size");
        final int[] size = new int[1];
        MyStatement<PreparedStatement> myStatement = (st) -> {
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                size[0] += resultSet.getInt("count");
            }
            return st;
        };
        execute(myStatement, "SELECT COUNT(*) AS count FROM resume");
        return size[0];
    }

    private void execute(MyStatement statement, String sql) {
        sqlHelper.execute(statement, sql);
    }
}

