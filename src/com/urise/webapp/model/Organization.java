package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This is class experience and educations
 */
public class Organization {
    private final Link homePage;
    private List<Position> descriptions;

    public Organization(String name, String url, List<Position> descriptions) {
        Objects.requireNonNull(name, "name must not be null");
        homePage = new Link(name, url);
        this.descriptions = descriptions;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getDescriptions() {
        return new ArrayList<>(descriptions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!Objects.equals(homePage, that.homePage)) return false;
        return Objects.equals(descriptions, that.descriptions);
    }

    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + (descriptions != null ? descriptions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", descriptions=" + descriptions +
                '}';
    }
}