package com.urise.webapp.storage;


import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void doSaveArray(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void doDeleteArray(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected Integer getIndex(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    return i;
                }
            }
        }
        return -1;
    }
}