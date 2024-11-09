package nl.hu.inno.hulp.domain;

import jakarta.persistence.Embeddable;
import nl.hu.inno.hulp.presentation.controllers.NotFoundException;

import java.util.Objects;

@Embeddable
public class StudentID {

    private String studentNumber;

    public StudentID(String studentNumber) throws NotFoundException {
        if (!isValid(studentNumber)) {
            throw new NotFoundException("Invalid Student ID: " + studentNumber);
        }
        this.studentNumber = studentNumber;
    }

    protected StudentID(){}

    private boolean isValid(String studentNumber) {
        return studentNumber.matches("^[0-9]{7}$");
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentID studentID = (StudentID) o;
        return studentNumber.equals(studentID.studentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNumber);
    }

    @Override
    public String toString() {
        return studentNumber;
    }

}
