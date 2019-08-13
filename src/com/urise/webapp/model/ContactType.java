package com.urise.webapp.model;

public enum ContactType {
    PHONE_NUMBER("Номер телефона"),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return "<a href = 'skype:" + value + "'>" + value + "</a>";
        }
    },
    EMAIL("Электронная почта") {
        @Override
        public String toHtml0(String value) {
            return "<a href = 'mailTo:" + value + "'>" + value + "</a>";
        }
    },
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

    protected String toHtml0(String value) {
        return title + ":" + value;
    }

    public String toHtml(String value) {
        return value == null ? "" : toHtml0(value);
    }
}
