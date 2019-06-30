package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

class OrganizationSection extends AbstractSection {
    private final List<AbstractSection> organizations;

    public OrganizationSection(List<AbstractSection> organizations) {
        Objects.requireNonNull(organizations, "Organizations must not be null");
        this.organizations = organizations;
    }

    public List<AbstractSection> getList() {
        return organizations;
    }

    public void addOrganizationElement(Organization section) {
        organizations.add(section);
    }

    @Override
    public String toString() {
        return organizations.toString();
    }
}
