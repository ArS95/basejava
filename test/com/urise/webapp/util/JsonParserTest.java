package com.urise.webapp.util;

import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SimpleTextSection;
import org.junit.Assert;
import org.junit.Test;

import static com.urise.webapp.TestData.RESUME_1;

public class JsonParserTest {

    @Test
    public void testResume() {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume);
    }

    @Test
    public void write() {
        AbstractSection expected = new SimpleTextSection("Arsen");
        String json = JsonParser.write(expected, AbstractSection.class);
        System.out.println(json);
        AbstractSection actual = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(expected, actual);
    }
}