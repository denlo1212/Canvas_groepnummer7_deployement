package nl.hu.inno.hulp.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Major {
    private String name;

    protected Major() {
    }

    public Major(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Major that = (Major) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}