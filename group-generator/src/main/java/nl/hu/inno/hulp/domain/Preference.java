package nl.hu.inno.hulp.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import nl.hu.inno.hulp.domain.id.StudentID;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Embeddable
public class Preference {

    @ElementCollection
    private List<StudentID> preferenceList;

    public Preference() {
        this.preferenceList = new ArrayList<>();
    }

    public void addStudentPreference(StudentID studentID) {
        this.preferenceList.add(studentID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Preference that = (Preference) o;
        return Objects.equals(preferenceList, that.preferenceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preferenceList);
    }

    @Override
    public String toString() {
        return "Preference{" +
                "preferenceList=" + preferenceList +
                '}';
    }
}
