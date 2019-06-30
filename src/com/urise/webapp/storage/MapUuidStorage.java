package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    private Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    protected void doUpdate(Resume resume, String key) {
        mapStorage.put(key, resume);
    }

    @Override
    protected void doSave(Resume resume, String key) {
        mapStorage.put(key, resume);
    }

    @Override
    protected Resume doGet(String key) {
        return mapStorage.get(key);
    }

    @Override
    protected void doDelete(String key) {
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
    protected boolean isExist(String key) {
        return mapStorage.containsKey(key);
    }

    @Override
    protected List<Resume> getCopyList() {
        return new ArrayList<>(mapStorage.values());
    }
}
