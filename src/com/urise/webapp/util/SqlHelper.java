package com.urise.webapp.util;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public static PreparedStatement getStatement(ConnectionFactory factory, String sql) {
        try (Connection connection = factory.getConnection()) {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
