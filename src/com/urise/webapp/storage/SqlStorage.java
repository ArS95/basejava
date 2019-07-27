package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.strategy.Helper.Helper;
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
    private Helper<MyStatement> sqlHelper;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    protected SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        PreparedStatement statement = getStatement("DELETE FROM resume");
        MyStatement myStatement = () -> statement;
        checkResult(myStatement);
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        PreparedStatement statement = getStatement("UPDATE resume SET full_name = ? WHERE uuid = ?");
        MyStatement myStatement = () -> {
            setString(statement, resume.getFullName(), resume.getUuid());
            return statement;
        };
        if (checkResult(myStatement)) {
            notExistException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        PreparedStatement statement = getStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)");
        MyStatement myStatement = () -> {
            setString(statement, resume.getUuid(), resume.getFullName());
            return statement;
        };
        if (checkResult(myStatement)) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    private void setString(PreparedStatement statement, String uuid, String full_name) throws SQLException {
        statement.setString(1, uuid);
        statement.setString(2, full_name);
    }

    private boolean checkResult(MyStatement statement) {
        return sqlHelper.executeUpdate(statement) != 1;
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        PreparedStatement statement = getStatement("SELECT * FROM resume r WHERE r.uuid = ?");
        Resume[] resume = new Resume[1];
        MyStatement myStatement = () -> {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            checkNext(resultSet, uuid);
            resume[0] = new Resume(uuid, resultSet.getString("full_name"));
            return statement;
        };
        executeQuery(myStatement);
        return resume[0];
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        PreparedStatement statement = getStatement("DELETE FROM resume WHERE uuid = ?");
        MyStatement myStatement = () -> {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            checkNext(resultSet, uuid);
            return statement;
        };
        executeQuery(myStatement);
    }

    private void checkNext(ResultSet resultSet, String uuid) throws SQLException {
        if (!resultSet.next()) {
            notExistException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GerAllSorted ");
        PreparedStatement statement = getStatement("SELECT * FROM resume");
        List<Resume> sortedList = new ArrayList<>();
        MyStatement myStatement = () -> {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sortedList.add(new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name")));
            }
            return statement;
        };
        executeQuery(myStatement);
        Collections.sort(sortedList);
        return sortedList;
    }

    @Override
    public int size() {
        PreparedStatement statement = getStatement("SELECT COUNT(*) AS count FROM resume");
        int[] size = {0};
        MyStatement myStatement = () -> {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                size[0] += resultSet.getInt("count");
            }
            return statement;
        };
        executeQuery(myStatement);
        return size[0];
    }

    private void executeQuery(MyStatement statement) {
        sqlHelper.executeQuery(statement);
    }

    private void notExistException(String uuid) {
        throw new NotExistStorageException(uuid);
    }

    private PreparedStatement getStatement(String sql) {
        return sqlHelper.getStatement(sql);
    }
}

