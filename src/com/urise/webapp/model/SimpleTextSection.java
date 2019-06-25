package com.urise.webapp.model;

/**
 * This is class personal and objective
 */
public class SimpleTextSection extends AbstractSection {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleTextSection that = (SimpleTextSection) o;

        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return "SimpleTextSection{" +
                "text='" + text + '\'' +
                '}';
    }
}
