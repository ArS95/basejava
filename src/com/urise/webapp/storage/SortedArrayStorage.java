package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("Resume " + resume.getUuid() + " not exist.");
        }
    }

    @Override
    public void save(Resume resume) {
        if (countResume != STORAGE_LIMIT) {
            String uuid = resume.getUuid();
            int index = getIndex(uuid);
            if (index <= 0) {
                storage[countResume] = resume;
                countResume++;
            } else {
                System.out.println("Resume " + resume.getUuid() + " already exist.");
            }
        } else {
            System.out.println("Storage overflow.");
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            for (int i = index; i < countResume - 1; i++) {
                storage[i] = storage[i + 1];
            }
            storage[countResume - 1] = null;
            countResume--;
        } else {
            System.out.println("Resume " + uuid + " not exist.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResume, searchKey);
    }
}
