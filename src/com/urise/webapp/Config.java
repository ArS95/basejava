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
    private static final String PROPS = "/resumes.properties";

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
        try (InputStream stream = Config.class.getResourceAsStream(PROPS)) {
            Properties properties = new Properties();
            properties.load(stream);
            storage_dir = new File(properties.getProperty("storage.dir"));
            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
            storage = new SqlStorage(url, user, password);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
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

//    private static File getHomeDir(){
//        String prop = System.getProperty("homeDir");
//        File homeDir = new File(prop == null?".": prop);
//        if (!homeDir.isDirectory()){
//            throw new IllegalStateException(homeDir + "is not directory");
//        }
//        return homeDir;
//    }
}
