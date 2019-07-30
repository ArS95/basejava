package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "Name1");
        RESUME_2 = new Resume(UUID_2, "Name2");
        RESUME_3 = new Resume(UUID_3, "Name3");
        RESUME_4 = new Resume(UUID_4, "Name4");

//        RESUME_1.addContact(ContactType.EMAIL, "mail1@ya.ru");
//        RESUME_1.addContact(ContactType.PHONE_NUMBER, "11111");
//        RESUME_1.addSection(SectionType.OBJECTIVE, new SimpleTextSection("Objective1"));
//        RESUME_1.addSection(SectionType.PERSONAL, new SimpleTextSection("Personal data"));
//        RESUME_1.addSection(SectionType.ACHIEVEMENT, new MarkedTextSection("Achivment11", "Achivment12", "Achivment13"));
//        RESUME_1.addSection(SectionType.QUALIFICATIONS, new MarkedTextSection("Java", "SQL", "JavaScript"));
//        RESUME_1.addSection(SectionType.EXPERIENCE,
//                new OrganizationSection(
//                        new Organization("Organization11", "http://Organization11.ru",
//                                new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
//                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
//        RESUME_1.addSection(SectionType.EDUCATION,
//                new OrganizationSection(
//                        new Organization("Institute", null,
//                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
//                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
//                        new Organization("Organization12", "http://Organization12.ru")));
//        RESUME_2.addContact(ContactType.SKYPE, "skype2");
//        RESUME_2.addContact(ContactType.PHONE_NUMBER, "22222");
//        RESUME_1.addSection(SectionType.EXPERIENCE,
//                new OrganizationSection(
//                        new Organization("Organization2", "http://Organization2.ru",
//                                new Organization.Position(2015, Month.JANUARY, "position1", "content1"))));
    }

    protected Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clearTest() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void updateTest() {
        Resume newResume = new Resume(UUID_3, "E");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(RESUME_4);
    }

    @Test
    public void saveTest() {
        int expectedSize = storage.size() + 1;
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(expectedSize);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistTest() {
        storage.save(RESUME_1);
    }

    @Test
    public void getTest() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistTest() {
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteTest() {
        int expectedSize = storage.size() - 1;
        storage.delete(UUID_1);
        assertSize(expectedSize);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAllSortedTest() {
        List<Resume> actualList = storage.getAllSorted();
        List<Resume> expectedList = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Collections.sort(expectedList);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sizeTest() {
        assertSize(3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}