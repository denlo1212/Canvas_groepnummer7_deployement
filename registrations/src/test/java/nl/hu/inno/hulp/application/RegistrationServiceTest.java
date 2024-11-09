package nl.hu.inno.hulp.application;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.data.CourseRepository;
import nl.hu.inno.hulp.api.ReadOnlyStudent;
import nl.hu.inno.hulp.domain.*;
import nl.hu.inno.hulp.domain.enums.RegistrationStatus;
import nl.hu.inno.hulp.domain.strategy.CompletedCourseValidate;
import nl.hu.inno.hulp.domain.strategy.RegistrationValidator;
import nl.hu.inno.hulp.domain.strategy.CombinedStrategy;
import nl.hu.inno.hulp.domain.strategy.ValidationResult;
import nl.hu.inno.hulp.presentation.dtos.request.RegistrationCheckValidtyDTO;
import nl.hu.inno.hulp.presentation.dtos.request.RegistrationRequestDto;
import nl.hu.inno.hulp.presentation.dtos.request.RegistrationUpdateDto;
import nl.hu.inno.hulp.presentation.dtos.response.RegistrationResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegistrationServiceTest {

    @Mock
    private CombinedStrategy combinedStrategy;

    @Mock
    private RegistrationValidator completedCourseValidate;

    @Mock
    private RegistrationValidator enteredCourseValidate;

    @Mock
    private RegistrationValidator mayorValidate;

    @Mock
    private ReadOnlyStudent readOnlyStudent;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private RegistrationService registrationService;

    private Student student;
    private StudentID studentID;
    private StudentID studentID2;
    private Course course;
    private CourseID courseID;
    private Course course2;
    private CourseID courseID2;
    private Major major;
    private Major TI;

    private Student updateStudent;

    @BeforeEach
    public void setUp() throws NotFoundException {
        MockitoAnnotations.openMocks(this);

        major = new Major("Software Development");
        TI = new Major("Technische Informatica");

        studentID = new StudentID("1888123");
        student = new Student(studentID, "Alice", "Wonderland", major);
        studentID2 = new StudentID("1888124");
        updateStudent = new Student(studentID2, "Alice", "Koala", major);

        courseID = new CourseID("24-TICT-SV3INNO-23");
        course = new Course(courseID, "Backend Development", "Backend", major);
        courseID2 = new CourseID("24-TICT-SV3INNO-24");
        course2 = new Course(courseID2, "Frontend Development", "Frontend", TI);

        when(combinedStrategy.getValidators()).thenReturn(Arrays.asList(
                completedCourseValidate,
                enteredCourseValidate,
                mayorValidate
        ));
    }

    @AfterEach
    void tearDown() {
        Registration.getPreviousRegistrations().clear();
    }

    @Test
    @DisplayName("Registration is valid")
    public void Registration_Valid() throws NotFoundException {
        // Arrange
        RegistrationRequestDto registrationRequestDto = new RegistrationRequestDto(studentID, courseID);
        when(readOnlyStudent.findByStudentID(studentID)).thenReturn(Optional.of(student));
        when(courseRepository.findByCourseID(courseID)).thenReturn(Optional.of(course));

        // Act
        RegistrationResponseDto registrationDTO = registrationService.newRegistration(registrationRequestDto);

        // Assert
        assertEquals(RegistrationStatus.ENTERED, registrationDTO.status());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    @DisplayName("CompletedCourseValidate should return failure when the student has already completed the course")
    public void testCompletedCourseValidate_StudentAlreadyCompleted() {
        // Arrange
        RegistrationCheckValidtyDTO registrationRequest = new RegistrationCheckValidtyDTO(studentID, courseID, student.getMajor().toString(), course.getMajor().toString());

        Registration completedRegistration = new Registration(studentID, courseID, student.getMajor().toString(), course.getMajor().toString(), new Date());
        completedRegistration.updateRegistration(RegistrationStatus.COMPLETED);
        Registration.getPreviousRegistrations().add(completedRegistration);

        CompletedCourseValidate validator = new CompletedCourseValidate();

        // Act
        ValidationResult result = validator.validate(registrationRequest);

        // Assert
        assertFalse(result.getStatusMessage().equals("Valid registration request."));
    }

    @Test
    @DisplayName("Throws NotFoundException when student is not found")
    public void Registration_StudentNotFound() throws NotFoundException {
        // Arrange
        RegistrationRequestDto registrationRequestDto = new RegistrationRequestDto(studentID, courseID);
        when(readOnlyStudent.findByStudentID(studentID)).thenReturn(Optional.empty());

        // Assert
        assertThrows(NotFoundException.class, () -> {
            registrationService.newRegistration(registrationRequestDto);
        });
    }

    @Test
    @DisplayName("Throws NotFoundException when course is not found")
    public void Registration_CourseNotFound() throws NotFoundException {
        // Arrange
        RegistrationRequestDto registrationRequestDto = new RegistrationRequestDto(studentID, courseID);
        when(readOnlyStudent.findByStudentID(studentID)).thenReturn(Optional.of(student));
        when(courseRepository.findByCourseID(courseID)).thenReturn(Optional.empty());

        // Assert
        assertThrows(NotFoundException.class, () -> {
            registrationService.newRegistration(registrationRequestDto);
        });
    }

    @Test
    @DisplayName("get registration")
    public void getRegistration() throws NotFoundException {
        // Arrange
        Registration registration = new Registration(studentID, courseID, student.getMajor().toString(), course.getMajor().toString(), new Date());
        registration.setId(1L);
        course.getRegistrations().add(registration);

        when(courseRepository.findCourseByRegistrationId(anyLong())).thenReturn(Optional.of(course));

        // Act
        RegistrationResponseDto registrationDTO = registrationService.getRegistration(1L);

        // Assert
        assertEquals(RegistrationStatus.ENTERED, registrationDTO.status());
    }

    @Test
    @DisplayName("Throws NotFoundException when registration is not found")
    public void getRegistration_NotFound() {
        // Arrange
        when(courseRepository.findCourseByRegistrationId(anyLong())).thenReturn(Optional.empty());

        // Assert
        assertThrows(NotFoundException.class, () -> {
            registrationService.getRegistration(1L);
        });
    }

    @Test
    @DisplayName("Update registration status")
    public void updateRegistration() throws NotFoundException {
        // Arrange
        Registration registration = new Registration(studentID, courseID, student.getMajor().toString(), course.getMajor().toString(), new Date());
        registration.setId(1L);
        course.getRegistrations().add(registration);
        when(courseRepository.findCourseByRegistrationId(anyLong())).thenReturn(Optional.of(course));

        // Act
        RegistrationResponseDto registrationDTO = registrationService.updateRegistration(1L, new RegistrationUpdateDto(RegistrationStatus.COMPLETED));

        // Assert
        assertEquals(RegistrationStatus.COMPLETED, registrationDTO.status());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    @DisplayName("Throws NotFoundException when registration is not found")
    public void updateRegistration_NotFound() {
        // Arrange
        when(courseRepository.findCourseByRegistrationId(anyLong())).thenReturn(Optional.empty());

        // Assert
        assertThrows(NotFoundException.class, () -> {
            registrationService.updateRegistration(1L, new RegistrationUpdateDto(RegistrationStatus.COMPLETED));
        });
    }

    @Test
    @DisplayName("Delete registration - Success")
    public void deleteRegistration() throws NotFoundException {
        // Arrange
        Registration registration = new Registration(studentID, courseID, student.getMajor().toString(), course.getMajor().toString(), new Date());
        registration.setId(1L);
        course.getRegistrations().add(registration);

        when(courseRepository.findCourseByRegistrationId(registration.getId())).thenReturn(Optional.of(course));

        // Act
        RegistrationResponseDto response = registrationService.deleteRegistration(registration.getId());

        // Assert
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    @DisplayName("Throws NotFoundException when registration is not found")
    public void deleteRegistration_NotFound() {
        // Arrange
        when(courseRepository.findCourseByRegistrationId(anyLong())).thenReturn(Optional.empty());

        // Assert
        assertThrows(NotFoundException.class, () -> {
            registrationService.deleteRegistration(1L);
        });
    }
}
