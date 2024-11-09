package nl.hu.inno.hulp.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import nl.hu.inno.hulp.application.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.hu.inno.hulp.domain.enums.RegistrationStatus;
import nl.hu.inno.hulp.presentation.dtos.request.CourseRequestDto;

@Entity
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private CourseID courseID;

    @Column(name = "name")
    private String name;
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "major_name"))
    })
    private Major major;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Registration> registrations;


    public Course(CourseID courseID, String name, String description, Major major) {
        this.courseID = courseID;
        this.name = name;
        this.description = description;
        this.major = major;
        this.registrations = new ArrayList<>();
    }

    public Course() {

    }

    public void updateCourse(CourseRequestDto courseRequestDto) throws NotFoundException {
        this.courseID = new CourseID(courseRequestDto.getCourseID());
        this.name = courseRequestDto.getName();
        this.description = courseRequestDto.getDescription();
        this.major = new Major(courseRequestDto.getMajor());
    }


    public Course registerStudent(StudentID studentID, String studentMajor, Date registrationDate) {
        Registration registration = new Registration(studentID, this.courseID, studentMajor, this.major.getName(), registrationDate);
        this.registrations.add(registration);
        return this;
    }

    public Course updateRegistration (RegistrationStatus status, CourseID courseID, StudentID studentID){
        Registration registration = this.registrations.stream()
                .filter(reg -> reg.getStudentID().equals(studentID) && reg.getCourseID().equals(courseID))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Registration not found"));
        registration.updateRegistration(status);
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CourseID getCourseID() {
        return courseID;
    }

    public String getDescription() {
        return description;
    }

    public Major getMajor() {
        return major;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void addRegistration(Registration registration) {
        registrations.add(registration);
    }

}