package com.urise.webapp.model;

import java.util.*;

/**
 * This is class achievement and qualifications
 */
public class MarkedTextSection extends AbstractSection {
    private List<String> stringList = new ArrayList<>();

    public void addTextElement(String element) {
        stringList.add(element);
    }

    public List<String> getAllText() {
        return new ArrayList<>(stringList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarkedTextSection that = (MarkedTextSection) o;

        return stringList.equals(that.stringList);
    }

    @Override
    public int hashCode() {
        return stringList.hashCode();
    }

    @Override
    public String toString() {
        return "MarkedTextSection{" +
                "stringList=" + stringList +
                '}';
    }
}
