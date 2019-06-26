package com.urise.webapp.model;

public class Contacts {
    private String contact;

    public Contacts(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contacts contacts = (Contacts) o;

        return contact.equals(contacts.contact);
    }

    @Override
    public int hashCode() {
        return contact.hashCode();
    }

    @Override
    public String toString() {
        return "{'" + contact + "'}";
    }
}