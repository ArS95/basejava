package com.urise.webapp.storage;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private static Storage sortedArrayStorage = new SortedArrayStorage();

    public SortedArrayStorageTest() {
        super(sortedArrayStorage);
    }
}