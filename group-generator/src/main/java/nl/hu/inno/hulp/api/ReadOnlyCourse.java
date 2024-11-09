package nl.hu.inno.hulp.api;

import nl.hu.inno.hulp.domain.Course;
import nl.hu.inno.hulp.presentation.controllers.NotFoundException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Optional;


public interface ReadOnlyCourse {

    Optional<Course> findById(String courseId) throws NotFoundException;
}
