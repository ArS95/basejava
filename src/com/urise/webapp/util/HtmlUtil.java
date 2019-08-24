package com.urise.webapp.util;

import com.urise.webapp.model.*;

public class HtmlUtil {
    public static String toHtmlSimpleTextSection(SectionType type, SimpleTextSection section) {
        return section == null || section.toString().trim().length() == 0 ? "" :
                "<dt><h2>" + type.getTitle() + ":</h2></dt><br>" +
                        "<dd>" + section + "</dd><br>";
    }

    public static String toHtmlMarkedTextSection(SectionType type, MarkedTextSection section) {
        return section == null || section.toString().trim().length() <= 2 ? "" :
                "<dt><h2>" + type.getTitle() + ":</h2></dt><br>" +
                        "<dd><ul><li>" +
                        String.join("</li><li>", section.getAllText()) +
                        "</li></ul></dd><br>";
    }

//    public static String toHtmlOrganizationSection(SectionType type, OrganizationSection section) {
//        if (section == null || section.toString().trim().length() <= 4 ){
//            return "";
//        }
//        StringBuilder builder = new StringBuilder();
//        builder.append("<dt><h2>");
//        builder.append(type.getTitle());
//        builder.append(":</h2></dt><br>");
//        section.getOrganizations().forEach(o-> {
//            builder.append("<h3><a href=\">" + o.getHomePage().getUrl() + "\">" + o.getHomePage().getName() + "</a>");
//            o.getPositions().forEach(p->{
//                builder.append()
//            });
//        });

//         builder.append(section == null || section.toString().trim().length() <= 4 ? "" :
//                "<dt><h2>" + builder.append(type.getTitle()) + ":</h2></dt><br>" +
//                        "<dd>" + section.getOrganizations().stream().map(s->{
//                            s.getHomePage();
//                            s.getPositions().forEach(p->{
//
//                            });
//                    return builder;
//                }) + "</dd>" +
//                        "<br>");
//         return String.valueOf(builder);
//    }

    public static String toHtmlContact(ContactType type, String value) {
        return value == null || value.trim().length() == 0 ? "" :
                "<dt><strong>" + type.getTitle() + ":</strong></dt>" +
                        "<dd>" + "<a href = \"" + value + "\">" + value + "</a>" + "</dd>" +
                        "<br>";
    }
}
