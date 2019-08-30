package com.urise.webapp.model;

public enum ContactType {
    PHONE_NUMBER("Номер телефона"),
    SKYPE("Skype"),
    EMAIL("Электронная почта"),
    LINKED_IN("Linked_In"),
    GIT_HUB("Git_Hub"),
    STACK_OVERFLOW("StackOverflow"),
    HOME_PAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
