package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);
    private Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clearTest() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void updateTest() {
        Resume expResume = new Resume(UUID_3);
        storage.update(expResume);
        assertSame(expResume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(new Resume());
    }

    @Test
    public void saveTest() {
        int expSize = storage.size() + 1;
        storage.save(RESUME_4);
        assertEquals(expSize, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistTest() {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void saveOverflowTest() {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            fail(e.getMessage());
        }
        storage.save(new Resume());
    }

    @Test
    public void getTest() {
        assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistTest() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteTest() {
        int expSize = storage.size() - 1;
        storage.delete(UUID_1);
        assertEquals(expSize, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete("dummy");
    }

    @Test
    public void getAllTest() {
        Resume[] resumeArray = new Resume[3];
        resumeArray[0] = RESUME_1;
        resumeArray[1] = RESUME_2;
        resumeArray[2] = RESUME_3;
        assertArrayEquals(resumeArray, storage.getAll());
    }

    @Test
    public void sizeTest() {
        assertEquals(3, storage.size());
    }
}