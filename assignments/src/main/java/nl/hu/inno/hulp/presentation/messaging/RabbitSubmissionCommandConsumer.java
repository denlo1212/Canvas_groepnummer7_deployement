package nl.hu.inno.hulp.presentation.messaging;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.data.SubmissionRepository;
import nl.hu.inno.hulp.logging.EventLogger;
import nl.hu.inno.hulp.presentation.dtos.response.SubmissionDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class RabbitSubmissionCommandConsumer {
    private final SubmissionRepository repository;

    public RabbitSubmissionCommandConsumer(SubmissionRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "submission.queue")
    @Transactional
    public void update(SubmissionDto dto) {
        try {
            repository.save(dto.toSubmission());
            EventLogger.info("Submission " + dto.id() + " has been updated.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

