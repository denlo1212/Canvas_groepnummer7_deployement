package nl.hu.inno.hulp.presentation.dtos.request;

import nl.hu.inno.hulp.domain.CourseID;
import nl.hu.inno.hulp.domain.StudentID;

public class RegistrationCheckValidtyDTO {
    private StudentID studentId;
    private CourseID courseId;

    private String studentMajor;
    private String courseMajor;

    public RegistrationCheckValidtyDTO(StudentID studentId, CourseID courseId, String studentMajor, String courseMajor) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.studentMajor = studentMajor;
        this.courseMajor = courseMajor;
    }

    public StudentID  getStudentId() {
        return studentId;
    }

    public CourseID getCourseId() {
        return courseId;
    }

    public String getStudentMajor() {
        return studentMajor;
    }

    public String getCourseMajor() {
        return courseMajor;
    }

}
