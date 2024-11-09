package nl.hu.inno.hulp.application.mock;

import nl.hu.inno.hulp.application.CourseService;
import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.presentation.dtos.request.CourseRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.CourseResponseDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//https://www.baeldung.com/spring-order

@Component
@Order(1)
public class MockDataLoader implements CommandLineRunner {
    private final CourseService courseService;

    public MockDataLoader(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void run(String... args) throws Exception {
        CourseRequestDto mockCourseRequest = new CourseRequestDto("24-TICT-SV3INNO-23", "Backend Development", "Backend", "Software Development");
        try {
            CourseResponseDto createdCourse = courseService.createCourse(mockCourseRequest);
            System.out.println("Mock course created: " + createdCourse.courseID());
        } catch (NotFoundException e) {
            System.out.println("Failed to create mock course: " + e.getMessage());
        }
    }
}

