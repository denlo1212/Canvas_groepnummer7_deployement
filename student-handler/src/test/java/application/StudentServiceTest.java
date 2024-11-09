package application;

import nl.hu.inno.hulp.application.StudentService;
import nl.hu.inno.hulp.domain.Major;
import nl.hu.inno.hulp.domain.Student;
import nl.hu.inno.hulp.domain.StudentID;
import nl.hu.inno.hulp.presentation.controllers.NotFoundException;
import nl.hu.inno.hulp.data.StudentRepository;
import nl.hu.inno.hulp.presentation.dtos.request.StudentRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.StudentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    private StudentService studentService;

    private StudentRepository studentRepository;

    private StudentDTO studentDTO;

    private StudentRequestDto studentRequestDto;

    private Student student;
    private Student preferedStudent;

    private StudentID fakeStudentID;
    private StudentID studentID;
    private StudentID studentID2;

    @BeforeEach
    void setUp() throws NotFoundException {
        studentRepository = mock(StudentRepository.class);
        studentService = new StudentService(studentRepository);

        studentRequestDto = new StudentRequestDto("1888123","John", "Doe", "Software Development");

        studentID = new StudentID("1888123");
        student = new Student(studentID,"John", "Doe", new Major("Software Development"));

        studentID2 = new StudentID("1888124");
        preferedStudent = new Student(studentID2, "Bob", "Smith", new Major("Software Development"));

        studentDTO = StudentDTO.build(student);

        fakeStudentID = new StudentID("1234567");
    }

    @Test
    @DisplayName("Create student test")
    void createStudent() throws NotFoundException {
        // Arrange
        when(studentRepository.save(student))
                .thenReturn(student);
        // Act
        StudentDTO result = studentService.createStudent(studentRequestDto);

        // Assert
        assertEquals(studentDTO, result);
    }

    @Test
    @DisplayName("Get student by id")
    void getStudent() throws NotFoundException {
        // Arrange
        when(studentRepository.findByStudentID(student.getStudentID()))
                .thenReturn(Optional.of(student));

        // Act
        StudentDTO result = studentService.getStudent(student.getStudentID().getStudentNumber());

        // Assert
        assertEquals(studentDTO, result);
    }

    @Test
    @DisplayName("Update student by id")
    void updateStudent() throws NotFoundException {
        // Arrange
        when(studentRepository.findById(0L))
                .thenReturn(Optional.of(student));
        when(studentRepository.save(student))
                .thenReturn(student);

        // Act
        StudentDTO result = studentService.updateStudent(0L, studentRequestDto);

        // Assert
        assertEquals(studentDTO, result);
    }

    @Test
    @DisplayName("Delete student by id")
    void deleteStudent() throws NotFoundException {
        // Arrange
        when(studentRepository.findById(0L))
                .thenReturn(Optional.of(student));

        // Act
        StudentDTO result = studentService.deleteStudent(0L);

        // Assert
        assertEquals(studentDTO, result);
    }

    @Test
    @DisplayName("Get student by id not found")
    void getStudentNotFound() {
        // Arrange
        when(studentRepository.findById(0L))
                .thenReturn(Optional.empty());

        // Act
        assertThrows(NotFoundException.class, () -> studentService.getStudent(fakeStudentID.getStudentNumber()));
    }
}