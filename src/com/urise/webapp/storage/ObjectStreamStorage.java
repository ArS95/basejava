package com.urise.webapp.storage;

import com.urise.webapp.strategy.StreamJavaSerializable;

import java.io.File;

public class ObjectStreamStorage extends AbstractFileStorage {
    public ObjectStreamStorage(File directory) {
        super(directory);
        strategy = new StreamJavaSerializable();
    }
}
