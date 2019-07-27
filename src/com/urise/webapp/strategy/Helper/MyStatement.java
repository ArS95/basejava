package com.urise.webapp.strategy.Helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface MyStatement {
    PreparedStatement execute() throws SQLException;
}