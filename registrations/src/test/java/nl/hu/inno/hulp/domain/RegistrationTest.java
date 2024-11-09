package nl.hu.inno.hulp.domain;

import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.domain.enums.RegistrationStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationTest {

    private Registration registration;
    private Student student;
    private Course course;

    private Major SD;

    private Major TI;

    private StudentID studentID;
    private CourseID courseID;

    private CourseID courseID2;

    private Course course2;

    @BeforeEach
    void setUp() throws NotFoundException {
        SD = new Major("Software Development");
        TI = new Major("Technische Informatica");
        studentID = new StudentID("1888123");
        courseID = new CourseID("24-TICT-SV3INNO-23");
        courseID2 = new CourseID("23-TICT-SV2INNO-23");
        student = new Student(studentID,"Alice", "Wonderland", SD);
        course = new Course(courseID,"Backend Development", "Backend", SD);
        course2 = new Course(courseID2, "ti", "ti", TI);

        Registration.getPreviousRegistrations().clear();
    }

    @AfterEach
    void tearDown() {
        Registration.getPreviousRegistrations().clear();
    }

    @Test
    @DisplayName("After good registration, status is ENTERED")
    public void testSuccessfulRegistration() {
        registration = new Registration(student.getStudentID(), course.getCourseID(),
                student.getMajor().getName(), course.getMajor().getName(),
                new Date());

        assertEquals(RegistrationStatus.ENTERED, registration.getStatus());
    }

    @Test
    @DisplayName("After bad registration, status is FAILED because of different majors")
    public void testFailedRegistration() {
        registration = new Registration(student.getStudentID(), course2.getCourseID(),
                student.getMajor().getName(), course2.getMajor().getName(),
                new Date());

        assertEquals(RegistrationStatus.FAILED, registration.getStatus());
    }

    @Test
    @DisplayName("Registration failed because student has already entered the course")
    public void testStudentAlreadyEnteredCourse() {
        registration = new Registration(student.getStudentID(), course.getCourseID(),
                student.getMajor().getName(), course.getMajor().getName(),
                new Date());

        Registration duplicateRegistration = new Registration(student.getStudentID(), course.getCourseID(),
                student.getMajor().getName(), course.getMajor().getName(),
                new Date());

        assertEquals(RegistrationStatus.FAILED, duplicateRegistration.getStatus());
    }

    @Test
    @DisplayName("Registration failed because student has already completed the course")
    public void testStudentAlreadyCompletedCourse() {
        registration = new Registration(student.getStudentID(), course.getCourseID(),
                student.getMajor().getName(), course.getMajor().getName(),
                new Date());
        registration.updateRegistration(RegistrationStatus.COMPLETED);

        Registration duplicateRegistration = new Registration(student.getStudentID(), course.getCourseID(),
                student.getMajor().getName(), course.getMajor().getName(),
                new Date());

        assertEquals(RegistrationStatus.FAILED, duplicateRegistration.getStatus());
    }

    @Test
    @DisplayName("Student can have 6 ENTERED registrations at the same time otherwise it will fail")
    public void testStudentCanHave6EnteredRegistrations() {
        for (int i = 0; i < 6; i++) {
            registration = new Registration(student.getStudentID(), course.getCourseID(),
                    student.getMajor().getName(), course.getMajor().getName(),
                    new Date());
            registration.updateRegistration(RegistrationStatus.ENTERED);
        }

        registration = new Registration(student.getStudentID(), course.getCourseID(),
                student.getMajor().getName(), course.getMajor().getName(),
                new Date());

        assertEquals(RegistrationStatus.FAILED, registration.getStatus());
    }
}