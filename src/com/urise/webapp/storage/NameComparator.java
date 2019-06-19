package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Comparator;

public class NameComparator implements Comparator<Resume> {

    @Override
    public int compare(Resume o1, Resume o2) {
        return o1.getFullName().compareTo(o2.getFullName());
    }
}
