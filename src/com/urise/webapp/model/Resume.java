package com.urise.webapp.model;

import java.util.*;

public class Resume {
    private final String uuid;
    private String fullName;
    private EnumMap<SectionType, AbstractSection> sectionsMap;
    private EnumMap<ContactType, Contacts> contactsMap;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        sectionsMap = new EnumMap<>(SectionType.class);
        contactsMap = new EnumMap<>(ContactType.class);
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
        return new EnumMap<>(contactsMap);
    }

    public Contacts getContact(ContactType contactEnum) {
        return contactsMap.get(contactEnum);
    }

    public void addContact(ContactType ContactType, Contacts contact) {
        contactsMap.put(ContactType, contact);
    }

    public Map<SectionType, AbstractSection> getAllSections() {
        return new EnumMap<>(sectionsMap);
    }

    public AbstractSection getSection(SectionType sectionType) {
        return sectionsMap.get(sectionType);
    }

    public void addSection(SectionType sectionType, AbstractSection section) {
        sectionsMap.put(sectionType, section);
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