package com.urise.webapp.model;

import java.util.*;

public class Resume {
    private final String uuid;
    private String fullName;
    private Contacts contacts = new Contacts();
    private Map<SectionType, Section> sectionMap;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        sectionMap = new HashMap<>();
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


    public Map<ContactEnum, String> getAllContacts() {
        return contacts.getAllContacts();
    }

    public String getContact(ContactEnum contactEnum) {
        return contacts.getContact(contactEnum);
    }

    public void addContact(ContactEnum type, String contact) {
        contacts.addContact(type, contact);
    }

    public Map<SectionType, Section> getAllSections() {
        return new HashMap<>(sectionMap);
    }

    public Section getSection(SectionType sectionType) {
        return sectionMap.get(sectionType);
    }

    public void addSection(SectionType sectionType, Section section) {
        sectionMap.put(sectionType, section);
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