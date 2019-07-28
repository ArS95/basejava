package com.urise.webapp.strategy.Helper;

import java.sql.SQLException;

@FunctionalInterface
public interface MyStatement<T> {
    T execute(T statement) throws SQLException;
}