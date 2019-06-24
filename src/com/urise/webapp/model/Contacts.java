package com.urise.webapp.model;

import java.util.HashMap;
import java.util.Map;

public class Contacts {
    private Map<ContactEnum, String> contactsMap = new HashMap<>();

    protected Map<ContactEnum, String> getAllContacts() {
        return new HashMap<>(contactsMap);
    }

    public void addContact(ContactEnum contactEnum, String contact) {
        contactsMap.put(contactEnum, contact);
    }

    public String getContact(ContactEnum contactEnum) {
        return contactsMap.get(contactEnum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contacts contacts1 = (Contacts) o;

        return contactsMap.equals(contacts1.contactsMap);
    }

    @Override
    public int hashCode() {
        return contactsMap.hashCode();
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "contactsMap=" + contactsMap +
                '}';
    }
}