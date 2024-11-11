package nl.hu.inno.hulp.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Teacher {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private TeacherID teacherID;

    private String firstName;
    private String lastName;

    public Teacher(
            String firstName,
            String lastName,
            String teacherNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.teacherID = new TeacherID(teacherNumber);

    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public TeacherID getTeacherID() {
        return teacherID;
    }

    public Teacher() {

    }

    @Override
    public String toString() {
        return "Teacher{" +
                ", firstName='" + firstName + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }
}