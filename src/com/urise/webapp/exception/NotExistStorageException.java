package com.urise.webapp.exception;

import java.sql.SQLException;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Resume " + uuid + " not exist", uuid);
    }

    public NotExistStorageException(SQLException e) {
        super(e);
    }

    public NotExistStorageException(String message, String uuid, Exception e) {
        super(message, uuid, e);
    }
}
