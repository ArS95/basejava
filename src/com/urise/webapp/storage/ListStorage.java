package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> listStorage = new ArrayList<>();

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Integer index) {
        listStorage.set(index, resume);
    }

    @Override
    protected void doSave(Resume resume, Integer index) {
        listStorage.add(resume);
    }

    @Override
    protected Resume doGet(Integer index) {
        return listStorage.get(index);
    }

    @Override
    protected void doDelete(Integer index) {
        listStorage.remove(index.intValue());
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
    protected boolean checkKey(Integer index) {
        return index >= 0;
    }

    @Override
    protected List<Resume> getCopyList() {
        return new ArrayList<>(listStorage);
    }
}
