package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        if (size != STORAGE_LIMIT) {
            String uuid = resume.getUuid();
            int index = getIndex(uuid);
            if (index < 0) {
                storage[size] = resume;
                size++;
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
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume " + uuid + " not exist.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    @Override
    protected int getIndex(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    return i;
                }
            }
        }
        return -1;
    }
}
