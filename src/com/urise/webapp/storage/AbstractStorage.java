package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK key = getExistedKey(resume.getUuid());
        doUpdate(resume, key);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK key = getNotExistedKey(resume.getUuid());
        doSave(resume, key);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK key = getExistedKey(uuid);
        return doGet(key);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK key = getExistedKey(uuid);
        doDelete(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        List<Resume> sortedList = getCopyList();
        Collections.sort(sortedList);
        return sortedList;
    }

    private SK getExistedKey(String uuid) {
        SK key = getSearchKey(uuid);
        if (!isExist(key)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private SK getNotExistedKey(String uuid) {
        SK key = getSearchKey(uuid);
        if (isExist(key)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected abstract List<Resume> getCopyList();

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK key);

    protected abstract void doSave(Resume resume, SK key);

    protected abstract void doDelete(SK key);

    protected abstract void doUpdate(Resume resume, SK key);

    protected abstract Resume doGet(SK key);
}