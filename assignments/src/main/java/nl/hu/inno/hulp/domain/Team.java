package nl.hu.inno.hulp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany
    private Set<Student> students = new HashSet<>();

    public Team(List<Student> students) {
        this.students.addAll(students);
    }

    public Team() {
    }

    public boolean allMembersApproved(Submission submission) {
        if (students.isEmpty()) {
            throw new IllegalArgumentException("No students in this team");
        }

        for (Student student : students) {
            if (!submission.isApprovedBy(student)) {
                return false;
            }
        }
        return true;
    }

    public void addStudent(Student student) {
        if (student != null) {
            students.add(student);
        }
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Long getId() {
        return id;
    }
}