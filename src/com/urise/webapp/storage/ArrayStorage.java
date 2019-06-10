package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static int countResume = 0;
    private Resume[] storage = new Resume[10000];

    public void clear() {
        for (int i = 0; i < countResume; i++) {
            storage[i] = null;
        }
        countResume = 0;
    }

    public int isCheckStorage(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < countResume; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void update(Resume r) {
        String uuid = r.getUuid();
        int count = isCheckStorage(uuid);
        if (count != -1) {
            storage[count] = r;
        } else {
            System.out.println("Resume is not find.");
        }
    }

    public void save(Resume r) {
        if (countResume != 10000) {
            String uuid = r.getUuid();
            int count = isCheckStorage(uuid);
            if (count == -1) {
                storage[countResume] = r;
                countResume++;
            } else {
                System.out.println("Resume is not saved, it have in storage.");
            }
        } else {
            System.out.println("Resume storage is full.");
        }
    }

    public Resume get(String uuid) {
        int count = isCheckStorage(uuid);
        if (count != -1) {
            return storage[count];
        } else {
            System.out.println("Resume is not find.");
        }
        return null;
    }

    public void delete(String uuid) {
        if (uuid != null) {
            boolean isDelete = false;
            for (int i = 0; i < countResume; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    isDelete = true;
                    for (int j = i; j < countResume - 1; j++) {
                        storage[j] = storage[j + 1];
                    }
                }
            }
            if (isDelete) {
                countResume--;
                storage[countResume] = null;
            } else {
                System.out.println("Resume is not find.");
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] onlyResume = new Resume[countResume];
        for (int i = 0; i < countResume; i++) {
            onlyResume[i] = storage[i];
        }
        return onlyResume;
    }

    public int size() {
        return countResume;
    }
}
