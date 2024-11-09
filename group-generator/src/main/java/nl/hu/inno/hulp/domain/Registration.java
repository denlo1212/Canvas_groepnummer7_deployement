package nl.hu.inno.hulp.domain;

import jakarta.persistence.*;
import nl.hu.inno.hulp.domain.id.CourseID;
import nl.hu.inno.hulp.domain.id.StudentID;

import java.util.Objects;

@Entity
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private StudentID studentID;

    @Embedded
    private Preference preference;

    @Embedded
    private CourseID courseID;

    public Registration(StudentID studentID, CourseID courseID) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.preference = new Preference();
    }

    protected Registration() {}

    public Long getId() {
        return id;
    }

    public Preference getPreference() {
        return preference;
    }

    public StudentID getStudentID() {
        return studentID;
    }

    public CourseID getCourseID() {
        return courseID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Registration that = (Registration) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(studentID, that.studentID) &&
                Objects.equals(preference, that.preference) &&
                Objects.equals(courseID, that.courseID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentID, preference, courseID);
    }

    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", studentID=" + studentID +
                ", preference=" + preference +
                ", courseID=" + courseID +
                '}';
    }
}
