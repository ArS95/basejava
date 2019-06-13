package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;


public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected static int size = 0;
    protected static int index;
    protected static int checkStorage = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("Resume " + resume.getUuid() + " not exist.");
        }
    }

    @Override
    public void save(Resume resume) {
        if (size != STORAGE_LIMIT) {
            if (resume != null) {
                String uuid = resume.getUuid();
                index = getIndex(uuid);
                if (index < 0) {
                    checkStorage++;
                } else {
                    System.out.println("Resume " + resume.getUuid() + " already exist.");
                }
            }
        } else {
            System.out.println("Storage overflow.");
        }
    }

    @Override
    public Resume get(String uuid) {
        index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Resume " + uuid + " not exist.");
            return null;
        }
    }

    @Override
    public void delete(String uuid) {
        index = getIndex(uuid);
        if (index >= 0) {
            checkStorage++;
        } else {
            System.out.println("Resume " + uuid + " not exist.");
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);

}