package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object key = getExistedKey(resume.getUuid());
        doUpdate(resume, key);
    }

    @Override
    public void save(Resume resume) {
        Object key = getNotExistedKey(resume.getUuid());
        doSave(resume, key);
    }

    @Override
    public Resume get(String uuid) {
        Object key = getExistedKey(uuid);
        return doGet(key);
    }

    @Override
    public void delete(String uuid) {
        Object key = getExistedKey(uuid);
        doDelete(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = getList();
        sortedList.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return sortedList;
    }

    private Object getExistedKey(String uuid) {
        Object key = getSearchKey(uuid);
        if (!checkKey(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private Object getNotExistedKey(String uuid) {
        Object key = getSearchKey(uuid);
        if (checkKey(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected abstract List<Resume> getList();

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean checkKey(Object key);

    protected abstract void doSave(Resume resume, Object key);

    protected abstract void doDelete(Object key);

    protected abstract void doUpdate(Resume resume, Object key);

    protected abstract Resume doGet(Object key);
}
