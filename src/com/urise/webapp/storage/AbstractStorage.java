package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public abstract void clear();

    @Override
    public abstract void update(Resume resume);

    @Override
    public abstract void save(Resume resume);

    @Override
    public abstract Resume get(String uuid);

    @Override
    public abstract void delete(String uuid);

    @Override
    public abstract Resume[] getAll();

    @Override
    public abstract int size();
}
