package nl.hu.inno.hulp.domain;

import jakarta.persistence.Embeddable;
import nl.hu.inno.hulp.application.exceptions.NotFoundException;

import java.util.Objects;

@Embeddable
public class CourseID {
    //24-TICT-SV3INNO-23
    //    validieer canvasjaargang, instituut, opleiding, cursusnummer, laatste aanpasdatum

    private String courseNumber;

    public CourseID(String courseNumber) throws NotFoundException {
        if (!isValid(courseNumber)) {
            throw new NotFoundException("Invalid Course ID: " + courseNumber);
        }
        this.courseNumber = courseNumber;
    }

    protected CourseID(){}

    public String getCourseNumber() {
        return courseNumber;
    }

    private boolean isValid(String courseNumber) {
        // Validatie: "24-TICT-SV3INNO-23" formaat
        // maak hem voor ICT
        return courseNumber.matches("^[0-9]{2}-[A-Z]{4}-[A-Z0-9]{7}-[0-9]{2}$");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseID courseID = (CourseID) o;
        return courseNumber.equals(courseID.courseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseNumber);
    }

    @Override
    public String toString() {
        return courseNumber;
    }
}
