package com.urise.webapp.model;

import java.util.Objects;

/**
 * This is class personal and objective
 */
public class SimpleTextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private String content;

    public SimpleTextSection() {
    }

    public SimpleTextSection(String content) {
        Objects.requireNonNull(content, "content must not be null");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleTextSection that = (SimpleTextSection) o;

        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public String toString() {
        return content;
    }
}