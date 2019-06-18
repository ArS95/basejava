package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        Object index = getIndex(uuid);
        if (isCheckIndex(index)) {
            doUpdate(resume, index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        Object index = getIndex(uuid);
        if (!isCheckIndex(index)) {
            doSave(resume, index);
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public Resume get(String uuid) {
        Object index = getIndex(uuid);
        if (isCheckIndex(index)) {
            return doGet(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        Object index = getIndex(uuid);
        if (isCheckIndex(index)) {
            doDelete(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract Object getIndex(String uuid);

    protected abstract boolean isCheckIndex(Object index);

    protected abstract void doSave(Resume resume, Object index);

    protected abstract void doDelete(Object index);

    protected abstract void doUpdate(Resume resume, Object index);

    protected abstract Resume doGet(Object index);
}
