package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object index = getExistedIndex(resume.getUuid());
        doUpdate(resume, index);
    }

    @Override
    public void save(Resume resume) {
        Object index = getNotExistedIndex(resume.getUuid());
        doSave(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        Object index = getExistedIndex(uuid);
        return doGet(index);
    }

    @Override
    public void delete(String uuid) {
        Object index = getExistedIndex(uuid);
        doDelete(index);
    }

    private Object getExistedIndex(String uuid) {
        Object index = getSearchKey(uuid);
        if (!checkKey(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    private Object getNotExistedIndex(String uuid) {
        Object index = getSearchKey(uuid);
        if (checkKey(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = getList();
        sortedList.sort(Comparator.comparing(Resume::getFullName));
        return sortedList;
    }

    protected abstract List<Resume> getList();

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean checkKey(Object index);

    protected abstract void doSave(Resume resume, Object index);

    protected abstract void doDelete(Object index);

    protected abstract void doUpdate(Resume resume, Object index);

    protected abstract Resume doGet(Object index);
}
