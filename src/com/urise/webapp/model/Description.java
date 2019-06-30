package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Description {
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String text;

    public Description(LocalDate startDate, LocalDate endDate, String title, String text) {
        Objects.requireNonNull(startDate, "startDaeDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.text = text;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Description that = (Description) o;

        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!Objects.equals(text, that.text)) return false;
        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Description{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", text='" + text + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}