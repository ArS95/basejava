package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        storage[(int) index] = resume;
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        if (size != STORAGE_LIMIT) {
            doSaveArray(resume, (Integer) index);
            size++;
        } else {
            throw new StorageException("Storage overflow.", resume.getUuid());
        }
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(int) index];

    }

    @Override
    protected void doDelete(Object index) {
        doDeleteArray((int) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = new ArrayList<>(Arrays.asList(Arrays.copyOf(storage, size)));
        sortedList.sort(new NameComparator());
        return sortedList;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isCheckIndex(Object index) {
        return index instanceof Integer && (Integer) index >= 0;
    }

    protected abstract void doSaveArray(Resume resume, int index);

    protected abstract void doDeleteArray(int index);

}