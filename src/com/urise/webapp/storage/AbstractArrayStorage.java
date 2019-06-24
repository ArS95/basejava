package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    protected void doSave(Resume resume, Integer index) {
        if (size != STORAGE_LIMIT) {
            doSaveArray(resume, index);
            size++;
        } else {
            throw new StorageException("Storage overflow.", resume.getUuid());
        }
    }

    @Override
    protected Resume doGet(Integer index) {
        return storage[index];

    }

    @Override
    protected void doDelete(Integer index) {
        doDeleteArray(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean checkKey(Integer index) {
        return index >= 0;
    }

    @Override
    protected List<Resume> getCopyList() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    protected abstract void doSaveArray(Resume resume, int index);

    protected abstract void doDeleteArray(int index);

}