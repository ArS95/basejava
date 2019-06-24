package com.urise.webapp.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This is class experience and education
 */
public class ComponentsSection extends Section<String, ComponentsEnum> {
    private Map<ComponentsEnum, String> componentsMap = new HashMap<>();

    @Override
    public void addSectionElement(String element, ComponentsEnum componentsEnum) {
        componentsMap.put(componentsEnum, element);
    }

    @Override
    public String getSection(ComponentsEnum componentsEnum) {
        return componentsMap.get(componentsEnum);
    }
    @Override
    public Map<ComponentsEnum, String> getAllElement() {
        return new HashMap<>(componentsMap);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComponentsSection that = (ComponentsSection) o;

        return componentsMap.equals(that.componentsMap);
    }

    @Override
    public int hashCode() {
        return componentsMap.hashCode();
    }

    @Override
    public String toString() {
        return "ComponentsSection{" +
                "componentsMap=" + componentsMap +
                '}';
    }
}
