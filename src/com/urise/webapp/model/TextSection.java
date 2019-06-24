package com.urise.webapp.model;

/**
 * This is class personal and objective
 */
public class TextSection extends Section<String, String> {
    private String text;

    @Override
    public void addSectionElement(String text, String key) {
        this.text = text;
    }

    @Override
    public String getSection(String key) {
        return text;
    }

    @Override
    public Object getAllElement() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "text='" + text + '\'' +
                '}';
    }
}
