package com.urise.webapp.model;

import java.util.*;

public class Resume {
    private final String uuid;
    private String fullName;
    private Map<SectionType, AbstractSection> sections;
    private Map<ContactType, Contacts> contacts;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        sections = new EnumMap<>(SectionType.class);
        contacts = new EnumMap<>(ContactType.class);
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

    public Map<ContactType, Contacts> getAllContacts() {
        return new EnumMap<>(contacts);
    }

    public Contacts getContact(ContactType contactEnum) {
        return contacts.get(contactEnum);
    }

    public void addContact(ContactType ContactType, Contacts contact) {
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