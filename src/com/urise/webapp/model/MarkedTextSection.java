package com.urise.webapp.model;

import java.util.*;

/**
 * This is class achievement and qualifications
 */
public class MarkedTextSection extends AbstractSection {
    private final List<String> items;

    public MarkedTextSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public void addTextElement(String element) {
        items.add(element);
    }

    public List<String> getAllText() {
        return new ArrayList<>(items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarkedTextSection that = (MarkedTextSection) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
