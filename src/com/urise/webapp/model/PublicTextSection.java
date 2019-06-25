package com.urise.webapp.model;

public class PublicTextSection extends AbstractSection {
    private String company;
    private String date;
    private String position;
    private String text;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PublicTextSection that = (PublicTextSection) o;

        if (!company.equals(that.company)) return false;
        if (!date.equals(that.date)) return false;
        if (!position.equals(that.position)) return false;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        int result = company.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PublicTextSection{" +
                "company='" + company + '\'' +
                ", date='" + date + '\'' +
                ", position='" + position + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
