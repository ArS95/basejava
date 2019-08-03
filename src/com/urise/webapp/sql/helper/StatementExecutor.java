package com.urise.webapp.sql.helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementExecutor<T> {
    T execute(PreparedStatement statement) throws SQLException;
}