package nl.hu.inno.hulp.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import nl.hu.inno.hulp.application.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Objects;


@Embeddable
public class Student {

    @Embedded
    private StudentID studentID;
    private String firstName;
    private String lastName;

    @Embedded
    @JsonProperty("Major")
    private Major major;
    @Id
    private Long id;

    public Student(StudentID studentID, String firstName, String lastName, Major major) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
    }

    public Student() {

    }

    public Major getMajor() {
        return major;
    }

    public StudentID getStudentID() {
        return studentID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName);
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", major=" + major +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}