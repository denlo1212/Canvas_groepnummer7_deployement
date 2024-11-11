package nl.hu.inno.hulp.domain;

import jakarta.persistence.*;
import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.presentation.dtos.request.StudentRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private StudentID studentID;
    private String firstName;
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Submission> submissions;

    public Student(StudentID studentID, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.submissions = new ArrayList<>();
    }

    public Student() {

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void updateStudent(StudentRequestDto studentRequestDto) throws NotFoundException {
        this.studentID = new StudentID(studentRequestDto.getStudentID());
        this.firstName = studentRequestDto.getFirstName();
        this.lastName = studentRequestDto.getLastName();
    }

    public StudentID getStudentID() {
        return studentID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && Objects.equals(submissions, student.submissions);
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}