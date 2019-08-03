package com.urise.webapp.sql.helper;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory factory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        factory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T execute(StatementExecutor<T> statement, String sql) {
        try (Connection connection = factory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return statement.execute(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T transactionExecute(SqlTransaction<T> executor) {
        try (Connection connection = factory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T res = executor.execute(connection);
                connection.commit();
                return res;
            } catch (SQLException e) {
                connection.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
