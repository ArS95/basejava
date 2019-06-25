package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Arsen");

        // Experience and Educations
        PublicTextSection experienceSection = new PublicTextSection();
        experienceSection.setCompany("JavaOps");
        experienceSection.setDate("24.06.2019 - present tense");
        experienceSection.setPosition("student");
        experienceSection.setText("\"Functional Programming Principles in Scala\" by Martin Odersky");

        PublicTextSection educationSection = new PublicTextSection();
        educationSection.setCompany("Google");
        educationSection.setDate("24.06.2015 - present tense");
        educationSection.setPosition("Middle developer");
        educationSection.setText("\"Google STADIA");


        // Achievement and Qualifications
        MarkedTextSection achievementList = new MarkedTextSection();
        achievementList.addTextElement('\n' + "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                '\n' + "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                '\n' + "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн " +
                "стажировок и ведение проектов. Более 1000 выпускников.");
        MarkedTextSection qualificationsList = new MarkedTextSection();
        qualificationsList.addTextElement('\n' + "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX," +
                '\n' + " DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, " +
                '\n' + "LDAP, OAuth1, OAuth2, JWT.");

        //Personal and Objective
        SimpleTextSection personal = new SimpleTextSection();
        personal.setText("Пурист кода и архитектуры.");

        SimpleTextSection objective = new SimpleTextSection();
        objective.setText("Ведущий стажировок и корпоративного обучения" +
                " по Java Web и Enterprise технологиям");


        // Add contacts
        resume.addContact(ContactType.PHONE_NUMBER, new Contacts("87472662282"));
        resume.addContact(ContactType.SKYPE, new Contacts("live:temirtasov95"));
        resume.addContact(ContactType.EMAIL, new Contacts("temirtasov@gmai.com"));
        resume.addContact(ContactType.LINKED_IN, new Contacts("https://www.linkedin.com/in/"));
        resume.addContact(ContactType.GIT_HUB, new Contacts("https://github.com/ArS95/basejava"));
        resume.addContact(ContactType.STACK_OVERFLOW, null);
        resume.addContact(ContactType.HOME_PAGE, new Contacts("https:/google.com/HOME_PAGE/kz"));

        //add Sections
        resume.addSection(SectionType.EXPERIENCE, experienceSection);
        resume.addSection(SectionType.EDUCATION, educationSection);
        resume.addSection(SectionType.ACHIEVEMENT, achievementList);
        resume.addSection(SectionType.QUALIFICATIONS, qualificationsList);
        resume.addSection(SectionType.PERSONAL, personal);
        resume.addSection(SectionType.OBJECTIVE, objective);


        for (Map.Entry<ContactType, Contacts> contact : resume.getAllContacts().entrySet()) {
            System.out.println(contact);
        }

        System.out.println("\n*********************************************************\n");

        for (Map.Entry<SectionType, AbstractSection> sections : resume.getAllSections().entrySet()) {
            System.out.println(sections);
        }

        System.out.println("\n*********************************************************\n");

        System.out.println(resume.getContact(ContactType.HOME_PAGE));
        System.out.println(resume.getSection(SectionType.ACHIEVEMENT));
        System.out.println(resume.getSection(SectionType.EDUCATION));
        System.out.println(resume.getContact(ContactType.EMAIL));
    }
}
