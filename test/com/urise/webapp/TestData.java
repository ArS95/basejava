package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final Resume RESUME_1;
    public static final Resume RESUME_2;
    public static final Resume RESUME_3;
    public static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "Name1");
        RESUME_2 = new Resume(UUID_2, "Name2");
        RESUME_3 = new Resume(UUID_3, "Name3");
        RESUME_4 = new Resume(UUID_4, "Name4");

        RESUME_1.addContact(ContactType.EMAIL, "mail1@ya.ru");
        RESUME_1.addContact(ContactType.PHONE_NUMBER, "11111");
        RESUME_1.addContact(ContactType.LINKED_IN, "Linked@ya.ru");
        RESUME_1.addContact(ContactType.GIT_HUB, "https://github.com/ArS95");
        RESUME_4.addContact(ContactType.PHONE_NUMBER, "555");
        RESUME_1.addContact(ContactType.SKYPE, "Sobaka@mail.ru");
        RESUME_1.addSection(SectionType.OBJECTIVE, new SimpleTextSection("Objective1"));
        RESUME_1.addSection(SectionType.PERSONAL, new SimpleTextSection("Personal data"));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new MarkedTextSection("Achivment11", "Achivment12", "Achivment13"));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new MarkedTextSection("Java", "SQL", "JavaScript"));
        RESUME_4.addSection(SectionType.QUALIFICATIONS, new MarkedTextSection("Java", "SQL", "JavaScript"));
        RESUME_1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        RESUME_1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));
        RESUME_2.addContact(ContactType.SKYPE, "skype2");
        RESUME_2.addContact(ContactType.PHONE_NUMBER, "22222");
        RESUME_1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization2", "http://Organization2.ru",
                                new Organization.Position(2015, Month.JANUARY, "position1", "content1"))));
    }
}
