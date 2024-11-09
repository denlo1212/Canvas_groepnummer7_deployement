package nl.hu.inno.hulp.domain;

import jakarta.persistence.*;
import nl.hu.inno.hulp.domain.id.GroupID;
import nl.hu.inno.hulp.domain.id.StudentID;
import nl.hu.inno.hulp.domain.id.TeacherID;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`group`")
public class Group {

    @EmbeddedId
    private GroupID groupID;

    @ElementCollection
    private List<TeacherID> teachers;

    @ElementCollection
    private List<StudentID> students;

    public Group(GroupID groupID) {
        this.groupID = groupID;
        this.teachers = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    public Group() {
    }

    public GroupID getGroupID() {
        return groupID;
    }

    public List<TeacherID> getTeachers() {
        return teachers;
    }

    public List<StudentID> getStudents() {
        return students;
    }

    public void addTeacher(TeacherID teacher) {
        this.teachers.add(teacher);
    }

    public void addStudents(List<StudentID> newStudents) {
        this.students.addAll(newStudents);
    }



}
