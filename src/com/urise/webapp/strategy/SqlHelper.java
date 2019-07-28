package com.urise.webapp.strategy;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.strategy.Helper.MyStatement;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory factory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        factory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void execute(MyStatement statement, String sql) {
        try (PreparedStatement preparedStatement = (PreparedStatement) statement.execute(factory.getConnection().prepareStatement(sql))) {
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
