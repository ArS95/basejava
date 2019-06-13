package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        if (size != STORAGE_LIMIT) {
            String uuid = resume.getUuid();
            int index = getIndex(uuid);
            if (index < 0) {
                int newIndex = -index - 1;
                System.arraycopy(storage, newIndex, storage, newIndex+1, size - newIndex + 1);
                storage[newIndex] = resume;
                size++;
            } else {
                System.out.println("Resume " + uuid + " already exist.");
            }
        } else {
            System.out.println("Storage overflow.");
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
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
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
