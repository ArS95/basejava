package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> listStorage = new ArrayList<>();

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        listStorage.set((Integer) index, resume);
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        listStorage.add(resume);
    }

    @Override
    protected Resume doGet(Object index) {
        return listStorage.get((Integer) index);
    }

    @Override
    protected void doDelete(Object index) {
        listStorage.remove((int) index);
    }

    @Override
    public int size() {
        return listStorage.size();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < listStorage.size(); i++) {
            if (listStorage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean checkKey(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    protected List<Resume> getList() {
        return listStorage;
    }
}
