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
            int contactsSize = dis.readInt();
            Resume resume = new Resume(uuid, fullName);
            for (int i = 0; i < contactsSize; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            while (dis.available() > 0) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new SimpleTextSection(dis.readUTF()));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int organizationsSize = dis.readInt();
                        List<Organization> organizations = new ArrayList<>();
                        for (int k = 0; k < organizationsSize; k++) {
                            Link link = new Link(dis.readUTF(), dis.readInt() == 0 ? dis.readUTF() : null);
                            int positionsSize = dis.readInt();
                            List<Organization.Position> positions = new ArrayList<>();
                            for (int j = 0; j < positionsSize; j++) {
                                positions.add(new Organization.Position(LocalDate.parse(dis.readUTF()),
                                        LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readInt() == 0 ? dis.readUTF() : null));
                            }
                            organizations.add(new Organization(link, positions));
                        }
                        resume.addSection(sectionType, new OrganizationSection(organizations));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        MarkedTextSection markedTextSection = new MarkedTextSection();
                        int allTextElementSize = dis.readInt();
                        for (int j = 0; j < allTextElementSize; j++) {
                            markedTextSection.addTextElement(dis.readUTF());
                        }
                        resume.addSection(sectionType, markedTextSection);
                        break;
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
            for (Map.Entry<SectionType, AbstractSection> entry : allSections.entrySet()) {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(entry.getValue().toString());
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = ((OrganizationSection) entry.getValue()).getOrganizations();
                        dos.writeInt(organizations.size());
                        for (Organization organization : organizations) {
                            Link link = organization.getHomePage();
                            dos.writeUTF(link.getName());
                            String url = link.getUrl();
                            if (url != null) {
                                dos.writeInt(0);
                                dos.writeUTF(url);
                            } else {
                                dos.writeInt(1);
                            }

                            List<Organization.Position> positions = organization.getPositions();
                            dos.writeInt(positions.size());
                            for (Organization.Position position : positions) {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                String description = position.getDescription();
                                if (description != null) {
                                    dos.writeInt(0);
                                    dos.writeUTF(description);
                                } else {
                                    dos.writeInt(1);
                                }
                            }
                        }
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> strings = ((MarkedTextSection) entry.getValue()).getAllText();
                        dos.writeInt(strings.size());
                        for (String text : strings) {
                            dos.writeUTF(text);
                        }
                        break;
                }
            }
        }
    }
}
