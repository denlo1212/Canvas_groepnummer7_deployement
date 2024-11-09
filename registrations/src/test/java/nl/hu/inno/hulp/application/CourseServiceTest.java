package nl.hu.inno.hulp.application;

import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.data.CourseRepository;
import nl.hu.inno.hulp.domain.Course;
import nl.hu.inno.hulp.domain.CourseID;
import nl.hu.inno.hulp.domain.Major;
import nl.hu.inno.hulp.presentation.dtos.request.CourseRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.CourseResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CourseServiceTest {

    private CourseService courseService;

    private CourseRepository courseRepository;

    private CourseRequestDto courseRequestDto;

    private CourseResponseDto courseResponseDto;

    private Course course;

    private CourseID courseID;

    private CourseID fakeCourseID;


    @BeforeEach
    void setup() throws NotFoundException {
        courseRepository = mock(CourseRepository.class);
        courseService = new CourseService(courseRepository);

        courseID = new CourseID("24-TICT-SV3INNO-23");
        courseRequestDto = new CourseRequestDto("24-TICT-SV3INNO-23", "Backend Development", "Backend", "Software Development");
        course = new Course(courseID, "Backend Development", "Backend", new Major("Software Development"));

        courseResponseDto = CourseResponseDto.build(course);

//        this is all so that classes can be formed

        fakeCourseID = new CourseID("19-TICT-SV3INNO-19");
    }


    @Test
    @DisplayName("Create course test")
    void createCourse() throws NotFoundException {
        // Arrange
        when(courseRepository.save(course))
                .thenReturn(course);
        // Act
        CourseResponseDto result = courseService.createCourse(courseRequestDto);

        // Assert
        assertEquals(courseResponseDto, result);
    }

    @Test
    @DisplayName("Get course by id")
    void getCourse() throws NotFoundException {
        // Arrange
        when(courseRepository.findByCourseID(course.getCourseID()))
                .thenReturn(Optional.of(course));
        // Act
        CourseResponseDto result = courseService.getCourse(course.getCourseID().getCourseNumber());

        // Assert
        assertEquals(courseResponseDto, result);
    }

    @Test
    @DisplayName("Get course by id not found")
    void getCourseNotFound() {
        // Arrange
        when(courseRepository.findById(0L))
                .thenReturn(Optional.empty());
        // Act
        assertThrows(NotFoundException.class, () -> courseService.getCourse(fakeCourseID.getCourseNumber()));
    }

    @Test
    @DisplayName("Update course by id")
    void updateCourse() throws NotFoundException {
        // Arrange
        when(courseRepository.findById(0L))
                .thenReturn(Optional.of(course));
        when(courseRepository.save(course))
                .thenReturn(course);
        // Act
        CourseResponseDto result = courseService.updateCourse(0L, courseRequestDto);

        // Assert
        assertEquals(courseResponseDto, result);
    }

    @Test
    @DisplayName("Update course by id not found")
    void updateCourseNotFound() {
        // Arrange
        when(courseRepository.findById(0L))
                .thenReturn(Optional.empty());
        // Act
        assertThrows(NotFoundException.class, () -> courseService.updateCourse(0L, courseRequestDto));
    }

    @Test
    @DisplayName("Delete course by id")
    void deleteCourse() throws NotFoundException {
        // Arrange
        when(courseRepository.findById(0L))
                .thenReturn(Optional.of(course));
        // Act
        CourseResponseDto result = courseService.deleteCourse(0L);

        // Assert
        assertEquals(courseResponseDto, result);
    }
}