package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        super.save(resume);
        if (checkStorage > 0) {
            int newIndex = -index - 1;
            System.arraycopy(storage, newIndex, storage, newIndex + 1, size - newIndex + 1);
            storage[newIndex] = resume;
            size++;
            checkStorage = 0;
        }
    }

    @Override
    public void delete(String uuid) {
        super.delete(uuid);
        if (checkStorage > 0) {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            storage[size - 1] = null;
            size--;
            checkStorage = 0;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
