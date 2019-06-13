package com.urise.webapp.storage;


import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        super.save(resume);
        if (checkStorage > 0) {
            storage[size] = resume;
            size++;
            checkStorage = 0;
        }
    }

    @Override
    public void delete(String uuid) {
        super.delete(uuid);
        if (checkStorage > 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            checkStorage = 0;
        }
    }

    @Override
    protected int getIndex(String uuid) {
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