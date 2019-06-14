package com.urise.webapp.storage;


import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume resume) {
        super.save(resume);
    }

    @Override
    public void delete(String uuid) {
        super.delete(uuid);
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

    @Override
    protected void saved(Resume resume, int index) {
        storage[size] = resume;
        size++;
    }

    @Override
    protected void deleted(int index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }
}