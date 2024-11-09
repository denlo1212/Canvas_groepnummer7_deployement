package nl.hu.inno.hulp.domain.id;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class TeacherID {

    private String teacherNumber;

    public TeacherID(String teacherNumber) {
        if (!isValid(teacherNumber)) {
            throw new IllegalArgumentException("Invalid Teacher ID: " + teacherNumber);
        }
        this.teacherNumber = teacherNumber;
    }

    protected TeacherID(){}

    private boolean isValid(String teacherNumber) {
        // Example validation: teacher number must be exactly 7 digits and start with 'T'
        return teacherNumber.matches("^T[0-9]{6}$");
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherID teacherID = (TeacherID) o;
        return teacherNumber.equals(teacherID.teacherNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherNumber);
    }

    @Override
    public String toString() {
        return teacherNumber;
    }
}
