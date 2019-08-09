package com.urise.webapp;

import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    //    private static final File PROPS = new File("config\\resumes.properties");
    private static final File PROPS = new File("C:\\Programming\\basejava\\config\\resumes.properties");

    private static final Config INSTANCE = new Config();
    private final File storage_dir;
    private final Storage storage;
    private String url;
    private String user;
    private String password;


    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream stream = new FileInputStream(PROPS)) {
            Properties properties = new Properties();
            properties.load(stream);
            storage_dir = new File(properties.getProperty("storage.dir"));
            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
            storage = new SqlStorage(url, user, password);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storage_dir;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Storage getStorage() {
        return storage;
    }
}
