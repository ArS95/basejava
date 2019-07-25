package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.util.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) throws SQLException {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM resume")) {
            SqlHelper.getStatement(connectionFactory,"DELETE FROM resume").execute();

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() != 1) {
                throw new NotExistStorageException(resume.getUuid());
            }
        } catch (SQLException e) {
            throw new NotExistStorageException(e);
        }
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
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
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
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
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
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
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume")) {
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
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS count FROM resume")) {
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
