package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.strategy.Helper;
import com.urise.webapp.strategy.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private Helper sqlHelper;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("Clear");
//        try (PreparedStatement statement = sqlHelper.getStatement("DELETE FROM resume")) {
//
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        PreparedStatement statement = sqlHelper.getStatement("DELETE FROM resume");
        sqlHelper.executeUpdate(statement);
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        try (PreparedStatement statement = sqlHelper.getStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            if (statement.executeUpdate() != 1) {
                throw new NotExistStorageException(resume.getUuid());
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        try (PreparedStatement ps = sqlHelper.getStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExistStorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        try (PreparedStatement ps = sqlHelper.getStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException("Resume " + uuid + "is not exist");
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new NotExistStorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        try (PreparedStatement ps = sqlHelper.getStatement("DELETE FROM resume WHERE uuid = ?")) {
            ps.setString(1, uuid);
            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new NotExistStorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GerAllSorted ");
        try (PreparedStatement ps = sqlHelper.getStatement("SELECT * FROM resume")) {
            ResultSet rs = ps.executeQuery();
            List<Resume> sortedList = new ArrayList<>();
            while (rs.next()) {
                sortedList.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            Collections.sort(sortedList);
            return sortedList;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        try (PreparedStatement ps = sqlHelper.getStatement("SELECT COUNT(*) AS count FROM resume")) {
            ResultSet resultSet = ps.executeQuery();
            int size = 0;
            while (resultSet.next()) {
                size += resultSet.getInt("count");
            }
            return size;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
interface MyStatement{
    void execute() throws SQLException;
}
