package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (index != -1) {
            storage[index] = resume;
        } else {
            System.out.println("Resume " + resume.getUuid() + " not exist.");
        }
    }

    public void save(Resume resume) {
        if (countResume != STORAGE_LIMIT) {
            String uuid = resume.getUuid();
            if (getIndex(uuid) == -1) {
                storage[countResume] = resume;
                countResume++;
            } else {
                System.out.println("Resume " + resume.getUuid() + "already exist.");
            }
        } else {
            System.out.println("Storage overflow.");
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            storage[index] = storage[countResume - 1];
            storage[countResume - 1] = null;
            countResume--;
        } else {
            System.out.println("Resume " + uuid + " not exist.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    protected int getIndex(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < countResume; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    return i;
                }
            }
        }
        return -1;
    }
}
