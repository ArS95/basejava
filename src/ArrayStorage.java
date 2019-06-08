/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private static int countResume = 0;

    void clear() {
        for (int i = 0; i < countResume; i++) {
            storage[i] = null;
        }
        countResume = 0;
    }

    void save(Resume r) {
        String uuid = r.uuid;
        if (uuid != null) {
            for (int i = 0; i < countResume; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    return;
                }
            }
            storage[countResume] = r;
            countResume++;

        }
    }

    Resume get(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < countResume; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    return storage[i];
                }
            }
        }
        return null;
    }

    void delete(String uuid) {
        if (uuid != null) {
            boolean isDelete = false;
            for (int i = 0; i < countResume; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    isDelete = true;
                    for (int j = i; j < countResume - 1; j++) {
                        storage[j] = storage[j + 1];
                    }
                }
            }
            if (isDelete) {
                countResume--;
                storage[countResume] = null;

            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] onlyResume = new Resume[countResume];
        for (int i = 0; i < countResume; i++) {
            onlyResume[i] = storage[i];
        }
        return onlyResume;
    }

    int size() {
        return countResume;
    }
}
