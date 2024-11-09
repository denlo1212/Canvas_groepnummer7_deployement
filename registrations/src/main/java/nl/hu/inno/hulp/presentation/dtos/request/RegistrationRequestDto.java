package nl.hu.inno.hulp.presentation.dtos.request;

import nl.hu.inno.hulp.domain.CourseID;
import nl.hu.inno.hulp.domain.StudentID;

public class RegistrationRequestDto {

    private StudentID studentId;
    private CourseID courseId;

    public RegistrationRequestDto(StudentID studentId, CourseID courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public StudentID getStudentId() {
        return studentId;
    }

    public CourseID getCourseId() {
        return courseId;
    }
}
