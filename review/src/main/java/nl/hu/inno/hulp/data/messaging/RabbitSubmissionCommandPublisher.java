package nl.hu.inno.hulp.data.messaging;

import nl.hu.inno.hulp.domain.Submission;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitSubmissionCommandPublisher implements SubmissionCommandPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final String DIRECT_EXCHANGE = "direct.exchange";
    private final String SUBMISSION_ROUTING_KEY = "submission.key";

    public RabbitSubmissionCommandPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void update(Submission submission) {
        rabbitTemplate.convertAndSend(
                DIRECT_EXCHANGE,
                SUBMISSION_ROUTING_KEY,
                SubmissionDto.build(submission));
    }
}
