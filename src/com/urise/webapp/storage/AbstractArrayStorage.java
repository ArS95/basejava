package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100_000;
    protected static int countResume = 0;
    public Resume[] storage = new Resume[STORAGE_LIMIT];

    public int size() {
        return countResume;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            System.out.println("Resume " + uuid + " not exist.");
            return null;
        }
    }

    protected abstract int getIndex(String uuid);
}
