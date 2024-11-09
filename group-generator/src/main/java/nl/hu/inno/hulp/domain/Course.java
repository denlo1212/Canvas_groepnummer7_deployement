package nl.hu.inno.hulp.domain;

import jakarta.persistence.*;
import nl.hu.inno.hulp.domain.id.CourseID;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {

    @EmbeddedId
    private CourseID courseID;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Registration> registrations = new ArrayList<>();

    protected Course() {}

    public Course(CourseID courseID) {
        this.courseID = courseID;
    }

    public CourseID getCourseID() {
        return courseID;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }
}
