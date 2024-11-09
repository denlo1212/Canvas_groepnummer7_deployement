package nl.hu.inno.hulp.presentation.dtos.response;

import nl.hu.inno.hulp.domain.Group;
import nl.hu.inno.hulp.domain.id.StudentID;
import nl.hu.inno.hulp.domain.id.TeacherID;

import java.util.ArrayList;
import java.util.List;

public record GroupDTO(
        String name,
        List<TeacherNumberDTO> teachers,
        List<StudentNumberDTO> students
) {

    public static GroupDTO build(Group group) {
        ArrayList<TeacherNumberDTO> teacherDTOS = new ArrayList<>();
        for(TeacherID teacher: group.getTeachers()){
            teacherDTOS.add(TeacherNumberDTO.build(teacher.getTeacherNumber()));
        }

        ArrayList<StudentNumberDTO> studentDTOS = new ArrayList<>();
        for(StudentID student: group.getStudents()){
            studentDTOS.add(StudentNumberDTO.build(student.getStudentNumber()));
        }

        return new GroupDTO(group.getGroupID().getGroupNumber(), teacherDTOS, studentDTOS);
    }
}