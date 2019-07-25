package com.urise.webapp.storage;

import com.urise.webapp.Config;

import java.sql.SQLException;


public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() throws SQLException {
        super(new SqlStorage(Config.get().getUrl(), Config.get().getUser(), Config.get().getPassword()));
    }
}