package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SortedArrayStorage;
import com.urise.webapp.storage.Storage;

public class MainTestArrayStorage {
    private static final Storage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume();
        r1.setUuid("uuid1");
        final Resume r2 = new Resume();
        r2.setUuid("uuid2");
        final Resume r3 = new Resume();
        r3.setUuid("uuid3");

        SORTED_ARRAY_STORAGE.save(r1);
        SORTED_ARRAY_STORAGE.save(r2);
        SORTED_ARRAY_STORAGE.save(r3);
        SORTED_ARRAY_STORAGE.update(r1);

        System.out.println("Get r1: " + SORTED_ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + SORTED_ARRAY_STORAGE.get("dummy"));

        printAll();
        SORTED_ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        SORTED_ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : SORTED_ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}