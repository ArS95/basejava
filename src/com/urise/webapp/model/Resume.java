package com.urise.webapp.model;

import java.io.Serializable;
import java.util.*;

public class Resume implements Serializable {
    private static final long serialVersionUID = 1L;
    private final java.lang.String uuid;
    private java.lang.String fullName;
    private Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    public Resume(java.lang.String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(java.lang.String uuid, java.lang.String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public java.lang.String getFullName() {
        return fullName;
    }

    public void setFullName(java.lang.String fullName) {
        this.fullName = fullName;
    }

    public java.lang.String getUuid() {
        return uuid;
    }

    public Map<ContactType, String> getAllContacts() {
        return new EnumMap<>(contacts);
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public void addContact(ContactType ContactType, String contact) {
        contacts.put(ContactType, contact);
    }

    public Map<SectionType, AbstractSection> getAllSections() {
        return new EnumMap<>(sections);
    }

    public AbstractSection getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public void addSection(SectionType sectionType, AbstractSection section) {
        sections.put(sectionType, section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public java.lang.String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}