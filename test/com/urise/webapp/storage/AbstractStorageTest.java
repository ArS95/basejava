package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.urise.webapp.TestData.*;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();

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
        Resume newResume = new Resume(UUID_1, "E");
        newResume.addContact(ContactType.PHONE_NUMBER,"456757");
        newResume.addContact(ContactType.EMAIL,"mail.ru");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
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
        final Resume actual = storage.get(resume.getUuid());
        assertEquals(resume, actual);
    }
}