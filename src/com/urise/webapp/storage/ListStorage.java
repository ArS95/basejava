package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> listStorage;

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume);
        if (index >= 0) {
            listStorage.set(index, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume);
        if (index < 0) {
            listStorage.add(resume);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(new Resume(uuid));
        if (index >= 0) {
            return listStorage.get(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(new Resume(uuid));
        if (index >= 0) {
            listStorage.remove(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return (Resume[]) listStorage.toArray();
    }

    @Override
    public int size() {
        return listStorage.size();
    }

    private int getIndex(Resume resume) {
        return listStorage.indexOf(resume);
    }
}
