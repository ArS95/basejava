package com.urise.webapp.storage;

        import com.urise.webapp.strategy.StreamSerializableStrategy;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new StreamSerializableStrategy()));
    }
}