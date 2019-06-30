package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.Arrays;
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
        Description Google1 = new Description(LocalDate.of(2015, 5, 1), LocalDate.of(2045, 1, 1),
                "Middle developer", "Google STADIA");
        Description Google2 = new Description(LocalDate.of(2045, 4, 1), LocalDate.of(2065, 2, 1),
                "Designer developer", "Google Chrome");
        Organization experience1 = new Organization("GOOGLE", "abv", Arrays.asList(Google1, Google2));


        Description Yandex1 = new Description(LocalDate.of(2050, 1, 1), LocalDate.of(2075, 3, 1),
                "Master developer", "Yandex STADIA");
        Description Yandex2 = new Description(LocalDate.of(2105, 2, 1), LocalDate.of(2035, 4, 1),
                "President", "Yandex Chrome");
        Organization experience2 = new Organization("YANDEX", "qwe", Arrays.asList(Yandex1, Yandex2));


        Description MGU1 = new Description(LocalDate.of(2090, 8, 1), LocalDate.of(2575, 5, 1),
                " student", "MGU STADIA");
        Description MGU2 = new Description(LocalDate.of(2195, 7, 1), LocalDate.of(2535, 6, 1),
                "MGU developer", "MGU Chrome");
        Organization education1 = new Organization("www", "abv", Arrays.asList(MGU1, MGU2));


        Description KSTU1 = new Description(LocalDate.of(2590, 9, 1), LocalDate.of(2875, 7, 1),
                "  KSTU student", "KSTU STADIA");
        Description KSTU2 = new Description(LocalDate.of(2995, 11, 1), LocalDate.of(2435, 8, 1),
                "KSTU developer", "KSTU Chrome");
        Organization education2 = new Organization("www", "abv", Arrays.asList(KSTU1, KSTU2));

        resume.addSection(SectionType.EXPERIENCE, experience1);
        resume.addSection(SectionType.EXPERIENCE, experience2);
        resume.addSection(SectionType.EDUCATION, education1);
        resume.addSection(SectionType.EDUCATION, education2);


        System.out.println("\n*********************************************************\n");

        for (Map.Entry<ContactType, Contacts> contact : resume.getAllContacts().entrySet()) {
            System.out.println(contact);
        }

        System.out.println("\n*********************************************************\n");

        for (Map.Entry<SectionType, AbstractSection> sections : resume.getAllSections().entrySet()) {
            System.out.println(sections);
        }

        System.out.println("\n*********************************************************");
    }
}