package com.urise.webapp.model;

import java.time.LocalDate;

/**
 * This is class experience and educations
 */
public class Description extends AbstractSection {
    private String company;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String position;
    private String text;

    public Description(String company, LocalDate beginDate, LocalDate endDate, String position, String text) {
        this.company = company;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.position = position;
        this.text = text;
    }

    public String getCompany() {
        return company;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getPosition() {
        return position;
    }

    public String getText() {
        return text;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Description that = (Description) o;

        if (!company.equals(that.company)) return false;
        if (!beginDate.equals(that.beginDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!position.equals(that.position)) return false;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        int result = company.hashCode();
        result = 31 * result + beginDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String date = beginDate.getMonthValue() + '/' + beginDate.getYear() + " - ";
        if (endDate == null) {
            date += "Сейчас";
        } else {
            date += endDate.getMonthValue() + '/' + endDate.getYear();
        }
        return '{' +
                "company='" + company + '\'' +
                ", date=" + date +
                ", position='" + position + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
