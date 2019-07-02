package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.strategy.Strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    protected Strategy strategy;
    private Path directory;

    protected AbstractPathStorage(String directory) {
        this.directory = Paths.get(directory);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(this.directory) || !Files.isWritable(this.directory)) {
            throw new IllegalArgumentException(directory + "is not directory or is not writable");
        }
    }

    @Override
    protected List<Resume> getCopyList() {
        try (Stream<Path> paths = Files.list(directory)) {
            List<Resume> copyList = new ArrayList<>();
            for (Path path : paths.collect(Collectors.toList())) {
                copyList.add(doGet(path));
            }
            return copyList;
        } catch (IOException e) {
            throw new StorageException("Stream exception", null, e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(String.valueOf(directory), uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path", null, e);
        }
        doUpdate(resume, path);
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", null, e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    protected void doWrite(Resume resume, OutputStream os) throws IOException {
        strategy.doWrite(resume, os);
    }

    protected Resume doRead(InputStream is) throws IOException {
        return strategy.doRead(is);
    }
}
