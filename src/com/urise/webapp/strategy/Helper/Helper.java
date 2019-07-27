package com.urise.webapp.strategy.Helper;

import java.sql.PreparedStatement;

public interface Helper<T> {
    PreparedStatement getStatement(String sql);

    int executeUpdate(T statement);

     void executeQuery(T statement);
}
