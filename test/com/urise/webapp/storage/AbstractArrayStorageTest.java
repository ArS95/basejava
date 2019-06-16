package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.size;
import static org.junit.Assert.*;

@Ignore
public abstract class AbstractArrayStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clearTest() {
        int expSize = 0;
        storage.clear();
        assertEquals(expSize, storage.getAll().length);
    }

    @Test
    public void updateTest() {
        Resume expResume = new Resume("UUID_4");
        storage.save(expResume);
        storage.update(expResume);
        assertEquals(expResume, storage.get("UUID_4"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(new Resume());
    }

    @Test
    public void saveTest() {
        try {
            int expSize = storage.size() + 1;
            Resume resume = new Resume("UUID_4");
            storage.save(resume);
            int befSize = storage.size();
            assertEquals(resume, storage.get("UUID_4"));
            assertEquals(expSize, befSize);
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorageException() {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void saveStorageException() {
        size = 10_000;
        storage.save(new Resume());
    }

    @Test
    public void getTest() {
        Resume resume = new Resume("UUID_4");
        storage.save(resume);
        assertEquals(resume, storage.get("UUID_4"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistTest() {
        storage.get("dummy");
    }

    @Test
    public void deleteTest() {
        int expSize = storage.size() - 1;
        storage.delete(UUID_1);
        int befSize = storage.size();
        assertEquals(expSize, befSize);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete("dummy");
    }

    @Test
    public void getAllTest() {
        Resume[] resumeArray = new Resume[3];
        resumeArray[0] = new Resume(UUID_1);
        resumeArray[1] = new Resume(UUID_2);
        resumeArray[2] = new Resume(UUID_3);
        assertArrayEquals(resumeArray, storage.getAll());
    }

    @Test
    public void sizeTest() {
        assertEquals(3, storage.size());
    }
}