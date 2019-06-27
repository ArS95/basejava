package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Arsen");

        // Add contacts
        resume.addContact(ContactType.PHONE_NUMBER, new Contacts("87472662282"));
        resume.addContact(ContactType.SKYPE, new Contacts("live:temirtasov95"));
        resume.addContact(ContactType.EMAIL, new Contacts("temirtasov@gmai.com"));
        resume.addContact(ContactType.LINKED_IN, new Contacts("https://www.linkedin.com/in/"));
        resume.addContact(ContactType.GIT_HUB, new Contacts("https://github.com/ArS95/basejava"));
        resume.addContact(ContactType.STACK_OVERFLOW, null);
        resume.addContact(ContactType.HOME_PAGE, new Contacts("https:/google.com/HOME_PAGE/kz"));

        //Personal and Objective
        SimpleTextSection personal = new SimpleTextSection("Пурист кода и архитектуры.");
        SimpleTextSection objective = new SimpleTextSection("Ведущий стажировок и корпоративного обучения" +
                " по Java Web и Enterprise технологиям.");
        resume.addSection(SectionType.PERSONAL, personal);
        resume.addSection(SectionType.OBJECTIVE, objective);

        // Achievement and Qualifications
        MarkedTextSection achievement = new MarkedTextSection(Arrays.asList("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                        "\n\t\t\t\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                        "\n\t\t\tУдаленное взаимодействие (JMS/AKKA)\". Организация онлайн " +
                        "\n\t\t\tстажировок и ведение проектов. Более 1000 выпускников.",
                        "\n\t\t\t\"Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                        "\n\t\t\tИнтеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.\""));

        MarkedTextSection qualifications = new MarkedTextSection(Arrays.asList("\"Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX," +
                        "\n\t\t\t  DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, " +
                        "\n\t\t\t  LDAP, OAuth1, OAuth2, JWT.\"",
                        "\n\t\t\t  \"MySQL, SQLite, MS SQL, HSQLDB\""));

        resume.addSection(SectionType.ACHIEVEMENT, achievement);
        resume.addSection(SectionType.QUALIFICATIONS, qualifications);

        // Experience and Educations
        Organization experience1 = new Organization("JavaOps", LocalDate.of(2015, 5, 1),
                null, "Middle developer", "\"Google STADIA");
        Organization experience2 = new Organization("Wrike", LocalDate.of(2008, 4, 1),
                LocalDate.of(2006, 5, 01), "Senior developer", "\"Google STADIA");
        Organization education1 = new Organization("Google", LocalDate.of(2010, 6, 1),
                LocalDate.of(2015, 12, 1), "student", "\"Functional Programming Principles in Scala\" by Martin Odersky");
        Organization education2 = new Organization("Yota", LocalDate.of(2010, 6, 1),
                LocalDate.of(2015, 12, 1), "architect", "\"Functional Programming Principles in Scala\" by Martin Odersky");

        resume.addSection(SectionType.EXPERIENCE, experience1);
        resume.addSection(SectionType.EXPERIENCE, experience2);
        resume.addSection(SectionType.EDUCATION, education1);
        resume.addSection(SectionType.EDUCATION, education2);


        System.out.println("\n*********************************************************\n");

        for (Map.Entry<ContactType, Contacts> contact : resume.getAllContacts().entrySet()) {
            System.out.println(contact);
        }

        System.out.println("\n*********************************************************\n");

        for (Map.Entry<SectionType, List<AbstractSection>> sections : resume.getAllSections().entrySet()) {
            System.out.println(sections);
        }

        System.out.println("\n*********************************************************");
    }
}
