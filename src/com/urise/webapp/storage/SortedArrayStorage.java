package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        if (size != STORAGE_LIMIT) {
            if (getIndex(resume.getUuid()) < 0) {
                if (size > 0) {
                    if (storage[size - 1].compareTo(resume) > 0) {
                        sort(resume);
                    } else {
                        storage[size] = resume;
                        size++;
                    }
                } else {
                    storage[0] = resume;
                    size++;
                }
            } else {
                System.out.println("Resume " + resume.getUuid() + " already exist.");
            }
        } else {
            System.out.println("Storage overflow.");
        }
    }

    private void sort(Resume resume) {
        storage[size] = resume;
        size++;
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int j = 0; j < size - 1; j++) {
                if (storage[j].compareTo(storage[j + 1]) > 0) {
                    Resume replace = storage[j];
                    storage[j] = storage[j + 1];
                    storage[j + 1] = replace;
                    isSorted = false;
                }
            }
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
