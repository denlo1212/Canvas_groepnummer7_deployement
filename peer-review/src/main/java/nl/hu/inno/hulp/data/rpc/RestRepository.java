package nl.hu.inno.hulp.data.rpc;

import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.domain.Assignment;
import nl.hu.inno.hulp.domain.Submission;
import nl.hu.inno.hulp.logging.EventLogger;
import nl.hu.inno.hulp.presentation.dtos.response.AssignmentResponseDto;
import nl.hu.inno.hulp.presentation.dtos.response.SubmissionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class RestRepository implements ReadOnlyRepository {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_ADDRESS_SUBMISSION = "http://localhost:8070/submission/";
    private final String BASE_ADDRESS_ASSIGNMENT = "http://localhost:8070/assignments/";

    @Override
    public Optional<Submission> findSubmissionById(Long id) {
        ResponseEntity<SubmissionResponseDto> response =
                restTemplate.getForEntity(BASE_ADDRESS_SUBMISSION + id, SubmissionResponseDto.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            SubmissionResponseDto dto = response.getBody();

            assert dto != null;
            EventLogger.info("Successful fetch: Submission with id: " + id);
            EventLogger.info("Submission status: " + dto.status());
            return Optional.of(dto.toSubmission());
        }
        EventLogger.error(
                "Unsuccessful fetch: Submission with id: " + id,
                new NotFoundException("Could not find submission with id: " + id)
        );
        return Optional.empty();
    }

    @Override
    public Optional<Assignment> findAssignmentById(Long id) {
        ResponseEntity<AssignmentResponseDto> response =
                restTemplate.getForEntity(BASE_ADDRESS_ASSIGNMENT + id, AssignmentResponseDto.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            AssignmentResponseDto dto = response.getBody();

            assert dto != null;
            EventLogger.info("Successful fetch: Assignment with id: " + id);
            EventLogger.info("Assignment title: " + dto.title());
            return Optional.of(dto.toAssignment());
        }
        EventLogger.error(
                "Unsuccessful fetch: Assignment with id: " + id,
                new NotFoundException("Could not find Assignment with id: " + id)
        );
        return Optional.empty();
    }
}
