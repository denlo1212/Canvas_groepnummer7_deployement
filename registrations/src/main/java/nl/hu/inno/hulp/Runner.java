package nl.hu.inno.hulp;

import nl.hu.inno.hulp.api.CourseProducer;
import nl.hu.inno.hulp.presentation.dtos.response.CourseResponseDto;
import nl.hu.inno.hulp.application.CourseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private final CourseProducer courseProducer;
    private final CourseService courseService;

    public Runner(CourseProducer courseProducer, CourseService courseService) {
        this.courseProducer = courseProducer;
        this.courseService = courseService;
    }

    @Override
    public void run(String... args) throws Exception {
        CourseResponseDto course = this.courseService.getCourse("24-TICT-SV3INNO-23");

//        this.courseProducer.sendCourse(course);
    }
}
