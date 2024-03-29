package com.urise.webapp.exception;

import java.sql.SQLException;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exist", uuid);
    }

    public ExistStorageException(SQLException e) {
        super(e);
    }
    public ExistStorageException(String message, String uuid, Exception e) {
        super(message, uuid, e);
    }
}
