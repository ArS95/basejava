package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private Map<Resume, String> mapStorage = new HashMap<>();

    @Override
    protected void doSave(Resume resume, Object index) {
        mapStorage.put(resume, resume.getUuid());
    }

    @Override
    protected void doDelete(Object index) {
        mapStorage.remove(index);
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        mapStorage.remove(index);
        mapStorage.put(resume, resume.getUuid());
    }

    @Override
    protected Resume doGet(Object index) {
        return (Resume) index;
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
    protected Resume getSearchKey(String uuid) {
        if (mapStorage.containsValue(uuid)) {
            for (Map.Entry<Resume, String> pair : mapStorage.entrySet()) {
                if (pair.getKey().getUuid().equals(uuid)) {
                    return pair.getKey();
                }
            }
        }
        return null;
    }

    @Override
    protected boolean checkKey(Object index) {
        return index != null;
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(mapStorage.keySet());
    }
}
