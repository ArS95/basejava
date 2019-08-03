package com.urise.webapp.sql.helper;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof SQLException) {
//            http://www.postgresql.org/docs/9.3/static/errcodes-appendix.html
            if (e.getSQLState().equals("23505")) {
                return new ExistStorageException(e);
            }
        }
        return new StorageException(e);
    }
}
