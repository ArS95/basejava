package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate of(String date) {
        return LocalDate.of(Integer.parseInt(date.substring(date.indexOf("/")  + 1)), Month.of(Integer.parseInt(date.substring(0, date.indexOf("/")))), 1);
    }
}
