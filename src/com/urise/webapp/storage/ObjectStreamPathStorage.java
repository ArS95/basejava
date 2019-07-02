package com.urise.webapp.storage;

import com.urise.webapp.strategy.StreamJavaSerializable;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    protected ObjectStreamPathStorage(String directory) {
        super(directory);
        strategy  = new StreamJavaSerializable();
    }
}
