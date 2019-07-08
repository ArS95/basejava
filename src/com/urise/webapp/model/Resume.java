package com.urise.webapp.model;

import java.io.Serializable;
import java.util.*;

public class Resume implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String uuid;
    private String fullName;
    private Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    public Resume(java.lang.String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid,String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUuid() {
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
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}