package nl.hu.inno.hulp.api;

import nl.hu.inno.hulp.application.CourseService;
import nl.hu.inno.hulp.data.CourseRepository;
import nl.hu.inno.hulp.domain.Course;
import nl.hu.inno.hulp.presentation.controllers.NotFoundException;
import nl.hu.inno.hulp.presentation.dtos.request.CourseRegistrationsDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class GenerateClassesListener {

    private final CourseRepository courseRepository;
    private final CourseService courseService;


    public GenerateClassesListener(CourseRepository courseRepository, CourseService courseService) {
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }

    @RabbitListener(queues = "course-queue")
    public void generateClassesPreferencesSubmitted(CourseRegistrationsDTO courseRegistrationsDTO) throws NotFoundException {
        if(courseRegistrationsDTO != null){
            Course course = CourseRegistrationConverter.convertToEntity(courseRegistrationsDTO);
            courseRepository.save(course); //todo gebruik course
            courseService.generateClasses(course);
        }
    }
}
