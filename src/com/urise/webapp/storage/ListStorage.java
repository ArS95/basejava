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
    public Resume[] getAll() {
        return listStorage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return listStorage.size();
    }

    @Override
    protected Integer getIndex(String uuid) {
        for (int i = 0; i < listStorage.size(); i++) {
            if (listStorage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isCheckIndex(Object index) {
        return index instanceof Integer && (Integer) index >= 0;
    }
}
