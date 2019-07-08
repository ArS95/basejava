package com.urise.webapp.strategy;

import com.urise.webapp.model.Resume;
import com.urise.webapp.util.JsonParser;

import java.io.*;

public class JsonStreamSerializer implements IOStrategy {
    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is)) {
            return JsonParser.read(reader, Resume.class);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os)) {
            JsonParser.write(resume, writer);
        }
    }
}
