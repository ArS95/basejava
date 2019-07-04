package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

public class ResumeTestData {
    public static void main(java.lang.String[] args) {
        Resume resume = new Resume("Arsen");

        // Add contacts
        resume.addContact(ContactType.PHONE_NUMBER, "87472662282");
        resume.addContact(ContactType.SKYPE, "live:temirtasov95");
        resume.addContact(ContactType.EMAIL, "temirtasov@gmai.com");
        resume.addContact(ContactType.LINKED_IN, "https://www.linkedin.com/in/");
        resume.addContact(ContactType.GIT_HUB, "https://github.com/ArS95/basejava");
        resume.addContact(ContactType.STACK_OVERFLOW, null);
        resume.addContact(ContactType.HOME_PAGE, "https:/google.com/HOME_PAGE/kz");

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
        Organization.Position Google1 = new Organization.Position(LocalDate.of(2015, 5, 1), LocalDate.of(2045, 1, 1),
                "Middle developer", "Google STADIA");
        Organization.Position Google2 = new Organization.Position(LocalDate.of(2045, 4, 1), LocalDate.of(2065, 2, 1),
                "Designer developer", "Google Chrome");
        Organization experience1 = new Organization("GOOGLE", "abv", Google1, Google2);


        Organization.Position Yandex1 = new Organization.Position(LocalDate.of(2050, 1, 1), LocalDate.of(2075, 3, 1),
                "Master developer", "Yandex STADIA");
        Organization.Position Yandex2 = new Organization.Position(LocalDate.of(2105, 2, 1), LocalDate.of(2035, 4, 1),
                "President", "Yandex Chrome");
        Organization experience2 = new Organization("YANDEX", "qwe", Yandex1, Yandex2);

        OrganizationSection experienceSection = new OrganizationSection(Arrays.asList(experience1, experience2));
        resume.addSection(SectionType.EXPERIENCE, experienceSection);

        Organization.Position MGU1 = new Organization.Position(LocalDate.of(2090, 8, 1), LocalDate.of(2575, 5, 1),
                " student", "MGU STADIA");
        Organization.Position MGU2 = new Organization.Position(LocalDate.of(2195, 7, 1), LocalDate.of(2535, 6, 1),
                "MGU developer", "MGU Chrome");
        Organization education1 = new Organization("www", "abv", MGU1, MGU2);


        Organization.Position KSTU1 = new Organization.Position(LocalDate.of(2590, 9, 1), LocalDate.of(2875, 7, 1),
                "  KSTU student", "KSTU STADIA");
        Organization.Position KSTU2 = new Organization.Position(LocalDate.of(2995, 11, 1), LocalDate.of(2435, 8, 1),
                "KSTU developer", "KSTU Chrome");
        Organization education2 = new Organization("www", "abv", KSTU1, KSTU2);

        OrganizationSection educationScetion = new OrganizationSection(Arrays.asList(education1, education2));
        resume.addSection(SectionType.EDUCATION, educationScetion);


        System.out.println("\n*********************************************************\n");

        for (Map.Entry<ContactType, String> contact : resume.getAllContacts().entrySet()) {
            System.out.println(contact);
        }

        System.out.println("\n*********************************************************\n");

        for (Map.Entry<SectionType, AbstractSection> sections : resume.getAllSections().entrySet()) {
            System.out.println(sections);
        }

        System.out.println("\n*********************************************************");
    }
}