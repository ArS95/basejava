package com.urise.webapp.strategy;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;

import java.io.*;
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
        }
    }
}
