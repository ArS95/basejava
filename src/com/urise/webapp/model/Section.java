package com.urise.webapp.model;

public abstract class Section<T,K> {
    public abstract void addSectionElement(T element, K key);

    public abstract T getSection(K key);

    public abstract Object getAllElement();
}
