package com.urise.webapp.util;

import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.SectionType;

import java.io.IOException;

public class HtmlUtil {
    public static String toHtmlSection(SectionType type, AbstractSection section) throws IOException {
        return section == null ? "" :
                        "<dt><strong>" + type.getTitle() + ":</strong></dt>" +
                        "<dd>" + section + "</dd>" +
                        "<br>";
    }

    public static String toHtmlContact(ContactType type, String value) {
        return value == null ? "" :
                        "<dt><strong>" + type.getTitle() + ":</strong></dt>" +
                        "<dd>" + "<a href = \"" + value + "\">" + value + "</a>" + "</dd>" +
                        "<br>";
    }
}
