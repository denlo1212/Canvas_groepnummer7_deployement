package nl.hu.inno.hulp.api;

import nl.hu.inno.hulp.presentation.dtos.response.SharedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import nl.hu.inno.hulp.presentation.dtos.response.CourseResponseDto;

@Component
public class CourseProducer {
    private final RabbitTemplate rabbitTemplate;

    public CourseProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCourse(CourseResponseDto courseResponseDto) {
        try {
            rabbitTemplate.convertAndSend("course", "course-key", courseResponseDto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send course to queue");
        }
    }

    public void sendMessage(SharedMessage sharedMessage){
        rabbitTemplate.convertAndSend("course", "course-key", sharedMessage);
    }
}

