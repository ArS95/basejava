package com.urise.webapp.strategy;

import java.sql.PreparedStatement;

public interface Helper {
    PreparedStatement getStatement(String sql);
    void executeUpdate(PreparedStatement statement);
    void executeQuery(PreparedStatement statement);
}
