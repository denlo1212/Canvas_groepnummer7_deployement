package nl.hu.inno.hulp.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Preference {

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    private List<StudentID> preferenceList;

    public Preference() {
        this.preferenceList = new ArrayList<>();
    }



    public List<StudentID> getPreferenceList() {
        return preferenceList;
    }
}
