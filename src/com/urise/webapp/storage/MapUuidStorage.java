package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
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
        mapStorage.put((String) key, resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return mapStorage.get(key);
    }

    @Override
    protected void doDelete(Object key) {
        mapStorage.remove(key);
    }

    @Override
    public int size() {
        return mapStorage.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean checkKey(Object key) {
        return mapStorage.containsKey(key);
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(mapStorage.values());
    }
}
