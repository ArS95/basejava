package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return (Resume) key;
    }

    @Override
    protected void doDelete(Object key) {
        Resume resume = (Resume) key;
        mapStorage.remove(resume.getUuid());
    }

    @Override
    public int size() {
        return mapStorage.size();
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    protected boolean checkKey(Object key) {
        return key != null;
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(mapStorage.values());
    }
}
