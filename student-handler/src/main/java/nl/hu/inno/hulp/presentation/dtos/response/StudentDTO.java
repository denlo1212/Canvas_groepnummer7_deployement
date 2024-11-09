package nl.hu.inno.hulp.presentation.dtos.response;


import nl.hu.inno.hulp.domain.Student;
import nl.hu.inno.hulp.domain.StudentID;

public record StudentDTO(
        Long id,
        StudentID studentID,
        String firstName,
        String lastName,
        String Major
) {

    public static StudentDTO build(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getStudentID(),
                student.getFirstName(),
                student.getLastName(),
                student.getMajor().getName()
        );
    }
}