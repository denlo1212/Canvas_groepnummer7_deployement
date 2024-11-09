package nl.hu.inno.hulp.api.submission;

import nl.hu.inno.hulp.domain.Submission;
import nl.hu.inno.hulp.presentation.dtos.request.SubmissionDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitSubmissionCommandPublisher implements SubmissionCommandPublisher {
    private final RabbitTemplate rabbitTemplate;

    public RabbitSubmissionCommandPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void update(Submission submission) {
        String teacher_review_exchange = "teacher_review_topic_exchange";
        String routing_key = "submission.update.*";
        rabbitTemplate.convertAndSend(
                teacher_review_exchange,
                routing_key,
                SubmissionDto.build(submission));
    }
}
