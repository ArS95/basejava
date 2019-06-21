package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;


public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected void doSave(Resume resume, Object index) {
        mapStorage.put((String) index, resume);
    }

    @Override
    protected void doDelete(Object index) {
        mapStorage.remove(index);
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object index) {
        return mapStorage.get(index);
    }

    @Override
    public void clear() {
        mapStorage.clear();
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
    protected boolean checkKey(Object index) {
        return mapStorage.containsKey(index);
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(mapStorage.values());
    }
}
