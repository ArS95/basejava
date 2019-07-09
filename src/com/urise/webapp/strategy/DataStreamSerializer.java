package com.urise.webapp.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements IOStrategy {
    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            int size = dis.readInt();
            Resume resume = new Resume(uuid, fullName);
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            for (int i = 0; i < dis.readInt(); i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                if (sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)) {
                    resume.addSection(sectionType, new SimpleTextSection(dis.readUTF()));
                    continue;
                }
                if (sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)) {
                    int sizeOrg = dis.readInt();
                    List<Organization> organizations = new ArrayList<>();
                    for (int k = 0; k < sizeOrg; k++) { 
                        Link link = new Link(dis.readUTF(), dis.readUTF());

                        int sizePos = dis.readInt();
                        List<Organization.Position> positions = new ArrayList<>();
                        for (int j = 0; j < sizePos; j++) {
                            positions.add(new Organization.Position(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                        }
                        organizations.add(new Organization(link, positions));
                    }
                    resume.addSection(sectionType, new OrganizationSection(organizations));
                } else {
                    MarkedTextSection markedTextSection = new MarkedTextSection();
                    for (int j = 0; j < dis.readInt(); j++) {
                        markedTextSection.addTextElement(dis.readUTF());
                    }
                    resume.addSection(sectionType, markedTextSection);
                }
            }
            return resume;
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> allContacts = resume.getAllContacts();
            dos.writeInt(allContacts.size());
            for (Map.Entry<ContactType, String> entry : allContacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> allSections = resume.getAllSections();
            dos.writeInt(allSections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : allSections.entrySet()) {
                if (entry.getKey().equals(SectionType.PERSONAL) || entry.getKey().equals(SectionType.OBJECTIVE)) {
                    dos.writeUTF(entry.getKey().name());
                    dos.writeUTF(entry.getValue().toString());
                    continue;
                }
                if (entry.getKey().equals(SectionType.EXPERIENCE) || entry.getKey().equals(SectionType.EDUCATION)) {
                    OrganizationSection organizationSection = (OrganizationSection) entry.getValue();
                    List<Organization> organizations = organizationSection.getOrganizations();

                    int sizeOrg = organizations.size();
                    dos.writeInt(sizeOrg);
                    for (Organization organization : organizations) {
                        Link link = organization.getHomePage();
                        dos.writeUTF(link.getName());
                        dos.writeUTF(link.getUrl());

                        List<Organization.Position> positions = organization.getPositions();
                        int sizePos = positions.size();
                        dos.writeInt(sizePos);
                        for (Organization.Position position : positions) {
                            dos.writeUTF(position.getStartDate().toString());
                            dos.writeUTF(position.getEndDate().toString());
                            dos.writeUTF(position.getTitle());
                            dos.writeUTF(position.getDescription());
                        }
                    }
                } else {
                    MarkedTextSection markedTextSection = (MarkedTextSection) entry.getValue();
                    List<String> strings = markedTextSection.getAllText();
                    dos.writeInt(strings.size());
                    for (String text : strings) {
                        dos.writeUTF(text);
                    }
                }
            }
        }
    }

//    private AbstractSection getReadSection(DataInputStream dis) throws IOException {
//        int sizeOrg = dis.readInt();
//        List<Organization> organizations = new ArrayList<>();
//        for (int i = 0; i < sizeOrg; i++) {
//            Link link = new Link(dis.readUTF(), dis.readUTF());
//
//            int sizePos = dis.readInt();
//            List<Organization.Position> positions = new ArrayList<>();
//            for (int j = 0; j < sizePos; j++) {
//                positions.add(new Organization.Position(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
//            }
//            organizations.add(new Organization(link, positions));
//        }
//        return  new OrganizationSection(organizations);
//    }
}
