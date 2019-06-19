package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeUuidStorage extends AbstractStorage {
//    private Map<Resume, String> mapStorage = new HashMap<>();
    private Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected void doSave(Resume resume, Object index) {
//      mapStorage.put(resume, resume.getUuid());
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Object index) {
//        mapStorage.remove(index);
        for (Map.Entry<String, Resume> stringResumeEntry : mapStorage.entrySet()) {
            if (index.equals(stringResumeEntry.getValue())) {
                mapStorage.remove(stringResumeEntry.getKey());
                break;
            }
        }
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
//      mapStorage.remove(index);
//      mapStorage.put(resume, resume.getUuid());
        mapStorage.put(resume.getUuid(), resume);
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
    public List<Resume> getAllSorted() {
//        List<Resume> sortedList = new ArrayList<>(mapStorage.keySet());
        List<Resume> sortedList = new ArrayList<>(mapStorage.values());
        sortedList.sort(new NameComparator());
        return sortedList;
    }

    @Override
    public int size() {
        return mapStorage.size();
    }

    @Override
    protected Resume getIndex(String uuid) {
//        if (mapStorage.containsValue(uuid)) {
//            for (Map.Entry<Resume, String> pair : mapStorage.entrySet()) {
//                if (pair.getValue().equals(uuid)) {
//                    return pair.getKey();
//                }
//            }
//        }
//        return null;
        return mapStorage.get(uuid);
    }

    @Override
    protected boolean isCheckIndex(Object index) {
        return index instanceof Resume;
    }
}
