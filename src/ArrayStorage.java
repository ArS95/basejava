/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private static int arraySize = 0;

    void clear() {
        storage = null;
        arraySize = 0;
    }

    void save(Resume r) {
        if (storage == null) {
            storage = new Resume[10000];
        }
        if (r.uuid != null) {
            boolean isUuid = false;
            for (int i = 0; i < arraySize; i++) {
                if (storage[i].uuid.equals(r.uuid)) {
                    isUuid = true;
                }
            }
            if (!isUuid) {
                storage[arraySize] = r;
                arraySize++;
            }
        }
    }

    Resume get(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < arraySize; i++) {
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
            if (arraySize == 1) {
                if (storage[0].uuid.equals(uuid)) {
                    storage[0] = null;
                    isDelete = true;
                }
            } else if (arraySize == 10000 && storage[9999].uuid.equals(uuid)) {
                storage[9999] = null;
                isDelete = true;

            } else {
                for (int i = 0; i < arraySize; i++) {
                    if (storage[i].uuid.equals(uuid)) {
                        for (int j = i; j < arraySize - 1; j++) {
                            storage[j] = storage[j + 1];
                            isDelete = true;
                        }
                    }
                }
            }

            if (isDelete) {
                arraySize--;
            }
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        if (storage != null) {
            Resume[] onlyResume = new Resume[arraySize];
            for (int i = 0; i < arraySize; i++) {
                onlyResume[i] = storage[i];
            }
            return onlyResume;
        }
        return new Resume[0];
    }

    int size() {
        return arraySize;
    }
}
