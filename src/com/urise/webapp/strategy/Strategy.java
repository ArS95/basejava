package com.urise.webapp.strategy;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Strategy {
    Resume doRead(InputStream is) throws IOException;

    void doWrite(Resume resume, OutputStream os) throws IOException;
}
