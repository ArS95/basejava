package com.urise.webapp.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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
            int allSectionsSize = dis.readInt();
            for (int i = 0; i < allSectionsSize; i++) {
                String type = dis.readUTF();
                SectionType sectionType = SectionType.valueOf(type);
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
                            Link link = new Link(dis.readUTF(), dis.readUTF());
                            int positionsSize = dis.readInt();
                            List<Organization.Position> positions = new ArrayList<>();
                            for (int j = 0; j < positionsSize; j++) {
                                positions.add(new Organization.Position(LocalDate.parse(dis.readUTF()),
                                        LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
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
            CollectionFunction<Map.Entry<ContactType, String>> mapContactFunction = map -> {
                dos.writeUTF(map.getKey().name());
                dos.writeUTF(map.getValue());
            };
            writeWithException(allContacts.entrySet(), dos, mapContactFunction);
            Map<SectionType, AbstractSection> allSections = resume.getAllSections();

            CollectionFunction<Map.Entry<SectionType, AbstractSection>> mapSectionFunction = map -> {
                SectionType sectionType = map.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(map.getValue().toString());
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = ((OrganizationSection) map.getValue()).getOrganizations();
                        CollectionFunction<Organization> orgFunction = org -> {
                            Link link = org.getHomePage();
                            dos.writeUTF(link.getName());
                            String url = link.getUrl();
                            if (url != null) {
                                dos.writeUTF(url);
                            } else {
                                dos.writeUTF("");
                            }

                            CollectionFunction<Organization.Position> posFunction = position -> {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                String description = position.getDescription();
                                if (description != null) {
                                    dos.writeUTF(description);
                                } else {
                                    dos.writeUTF("");
                                }
                            };
                            writeWithException(org.getPositions(), dos, posFunction);
                        };
                        writeWithException(organizations, dos, orgFunction);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> strings = ((MarkedTextSection) map.getValue()).getAllText();
                        CollectionFunction<String> strFunction = dos::writeUTF;
                        writeWithException(strings, dos, strFunction);
                        break;
                }
            };
            writeWithException(allSections.entrySet(), dos, mapSectionFunction);
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, CollectionFunction<T> collectionFunction) throws IOException {
        dos.writeInt(collection.size());
        for (T collect : collection) {
            collectionFunction.write(collect);
        }
    }
}

@FunctionalInterface
interface CollectionFunction<T> {
    void write(T t) throws IOException;
}