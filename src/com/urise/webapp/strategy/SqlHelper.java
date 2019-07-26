package com.urise.webapp.strategy;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper implements Helper {
    public final ConnectionFactory factory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        factory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public PreparedStatement getStatement(String sql)  {
        try {
            return factory.getConnection().prepareStatement(sql);
        } catch (SQLException e) {
           throw new StorageException(e);
        }
    }
    @Override
    public void executeUpdate(PreparedStatement statement){
        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executeQuery(PreparedStatement statement) {
        try {
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
