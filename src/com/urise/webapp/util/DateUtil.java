package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("MM/yyyy");
    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate of(String date) {
        YearMonth yearMonth = YearMonth.parse(date, FORMAT);
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);

    }
}
