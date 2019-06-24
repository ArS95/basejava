package com.urise.webapp.model;

import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Arsen");

        // Experience and Educations
        Section<String, ComponentsEnum> experienceSection = new ComponentsSection();
        experienceSection.addSectionElement("JavaOps", ComponentsEnum.COMPANY);
        experienceSection.addSectionElement("24.06.2019 - present tense", ComponentsEnum.DATE);
        experienceSection.addSectionElement("student", ComponentsEnum.POSITION);
        experienceSection.addSectionElement("\"Functional Programming Principles in Scala\" by Martin Odersky", ComponentsEnum.TEXT);

        Section<String, ComponentsEnum> educationSection = new ComponentsSection();
        educationSection.addSectionElement("Google", ComponentsEnum.COMPANY);
        educationSection.addSectionElement("24.06.2015 - present tense", ComponentsEnum.DATE);
        educationSection.addSectionElement("Middle developer", ComponentsEnum.POSITION);
        educationSection.addSectionElement("\"Google STADIA", ComponentsEnum.TEXT);


        // Achievement and Qualifications
        Section<String, List<String>> achievementList = new StringList();
        achievementList.addSectionElement('\n' +"С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                                                  '\n' + "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                                                  '\n' +   "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн " +
                                                  "стажировок и ведение проектов. Более 1000 выпускников.",null);
        Section<String, List<String>> qualificationsList = new StringList();
        qualificationsList.addSectionElement( '\n' + "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX," +
                                                      '\n' +   " DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, " +
                                                      '\n' +   "LDAP, OAuth1, OAuth2, JWT.",null);

        //Personal and Objective
        Section<String, String> personal = new TextSection();
        personal.addSectionElement("Пурист кода и архитектуры.", null);

        Section<String, String> objective = new TextSection();
        objective.addSectionElement("Ведущий стажировок и корпоративного обучения" +
                " по Java Web и Enterprise технологиям", null);


        // Add contacts
        resume.addContact(ContactEnum.PHONE_NUMBER, "87472662282");
        resume.addContact(ContactEnum.SKYPE, "live:temirtasov95");
        resume.addContact(ContactEnum.EMAIL, "temirtasov@gmai.com");
        resume.addContact(ContactEnum.LINKED_IN, "https://www.linkedin.com/in/");
        resume.addContact(ContactEnum.GIT_HUB, "https://github.com/ArS95/basejava");
        resume.addContact(ContactEnum.STACK_OVERFLOW, null);
        resume.addContact(ContactEnum.HOME_PAGE, null);

        //add Sections
        resume.addSection(SectionType.EXPERIENCE, experienceSection);
        resume.addSection(SectionType.EDUCATION, educationSection);
        resume.addSection(SectionType.ACHIEVEMENT, achievementList);
        resume.addSection(SectionType.QUALIFICATIONS, qualificationsList);
        resume.addSection(SectionType.PERSONAL, personal);
        resume.addSection(SectionType.OBJECTIVE, objective);


        for (Map.Entry<ContactEnum,String> contact : resume.getAllContacts().entrySet()) {
            System.out.println(contact);
        }
        System.out.println("\n*********************************************************\n");
        for (Map.Entry<SectionType,Section> sections : resume.getAllSections().entrySet()) {
            System.out.println(sections);
        }
        System.out.println("\n*********************************************************\n");
        System.out.println(resume.getContact(ContactEnum.HOME_PAGE));
        System.out.println(resume.getSection(SectionType.ACHIEVEMENT));
        System.out.println(resume.getSection(SectionType.EDUCATION).getAllElement());
    }
}
