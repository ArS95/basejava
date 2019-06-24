package com.urise.webapp.model;

import java.util.*;

/**
 * This is class achievement and qualifications
 */
public class StringList extends Section<String, List<String>> {
    private List<String> stringList = new ArrayList<>();

    @Override
    public void addSectionElement(String element, List<String> key) {
        stringList.add(element);
    }

    @Override
    public String getSection(List<String> key) {
        return null;
    }

    @Override
    public List<String> getAllElement() {
        return new ArrayList<>(stringList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringList that = (StringList) o;

        return stringList.equals(that.stringList);
    }

    @Override
    public int hashCode() {
        return stringList.hashCode();
    }

    @Override
    public String toString() {
        return "StringList{" +
                "stringList=" + stringList +
                '}';
    }
}
