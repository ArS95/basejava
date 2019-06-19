package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);
    protected Storage storage;

    @Before
    public void setUp() throws Exception {
        storage.clear();
        RESUME_1.setFullName("Zarinaa");
        RESUME_2.setFullName("Zarinaaa");
        RESUME_3.setFullName("Zarina");
        RESUME_4.setFullName("Arsen");
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
        Resume newResume = new Resume(UUID_3);
        newResume.setFullName("Azamat");
        storage.update(newResume);
        assertEquals(newResume.getFullName(), storage.get(UUID_3).getFullName());
        assertGet(newResume);
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
    public void saveExistTet() {
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
    public void getAllTest() {
        List<Resume> resumeActual = storage.getAllSorted();
        List<Resume> expectedList = new ArrayList<>(Arrays.asList(new Resume[]{RESUME_1, RESUME_2, RESUME_3}));
        expectedList.sort(new NameComparator());
        assertEquals(expectedList, resumeActual);
        assertEquals(3, resumeActual.size());
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