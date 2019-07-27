package com.urise.webapp.strategy;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.strategy.Helper.Helper;
import com.urise.webapp.strategy.Helper.MyStatement;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper implements Helper<MyStatement> {
    public final ConnectionFactory factory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        factory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public PreparedStatement getStatement(String sql) {
        try {
            return factory.getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int executeUpdate(MyStatement statement) {
        try (PreparedStatement preparedStatement = statement.execute()) {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExistStorageException(e);
        }
    }

    @Override
    public void executeQuery(MyStatement statement) {
        try (PreparedStatement preparedStatement = statement.execute()) {
        } catch (SQLException e) {
            throw new NotExistStorageException(e);
        }
    }
}
