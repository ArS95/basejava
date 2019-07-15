package com.urise.webapp.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@FunctionalInterface
interface CollectionWriteFunction<T> {
    void write(T t) throws IOException;
}

@FunctionalInterface
interface CollectionReadFunction {
    void read() throws IOException;
}

public class DataStreamSerializer implements IOStrategy {
    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();

            Resume resume = new Resume(uuid, fullName);
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new SimpleTextSection(dis.readUTF()));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        readWithException(dis, () -> {
                            Link link = new Link(dis.readUTF(), dis.readUTF());

                            List<Organization.Position> positions = new ArrayList<>();
                            readWithException(dis, () -> positions.add(new Organization.Position(LocalDate.parse(dis.readUTF()),
                                    LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF())));
                            organizations.add(new Organization(link, positions));
                        });
                        resume.addSection(sectionType, new OrganizationSection(organizations));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        MarkedTextSection markedTextSection = new MarkedTextSection();
                        readWithException(dis, () -> markedTextSection.addTextElement(dis.readUTF()));
                        resume.addSection(sectionType, markedTextSection);
                        break;
                }
            });
            return resume;
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> allContacts = resume.getAllContacts();
            writeWithException(allContacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, AbstractSection> allSections = resume.getAllSections();

            writeWithException(allSections.entrySet(), dos, entry -> {
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
                        writeWithException(organizations, dos, org -> {
                            Link link = org.getHomePage();
                            dos.writeUTF(link.getName());
                            String url = link.getUrl();
                            if (url != null) {
                                dos.writeUTF(url);
                            } else {
                                dos.writeUTF("");
                            }

                            writeWithException(org.getPositions(), dos, position -> {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                String description = position.getDescription();
                                if (description != null) {
                                    dos.writeUTF(description);
                                } else {
                                    dos.writeUTF("");
                                }
                            });
                        });
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> strings = ((MarkedTextSection) entry.getValue()).getAllText();
                        writeWithException(strings, dos, dos::writeUTF);
                        break;
                }
            });
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, CollectionWriteFunction<T> collectionWriteFunction) throws IOException {
        dos.writeInt(collection.size());
        for (T collect : collection) {
            collectionWriteFunction.write(collect);
        }
    }

    private void readWithException(DataInputStream dis, CollectionReadFunction collectionReadFunction) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            collectionReadFunction.read();
        }
    }
}