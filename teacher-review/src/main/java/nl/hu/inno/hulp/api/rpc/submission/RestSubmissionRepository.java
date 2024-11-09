package nl.hu.inno.hulp.api.rpc.submission;

import nl.hu.inno.hulp.domain.Submission;
import nl.hu.inno.hulp.presentation.dtos.response.SubmissionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class RestSubmissionRepository implements ReadOnlySubmissionRepository {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<Submission> findById(Long id) {
        String url = "http://localhost:8060/submission/";
        ResponseEntity<SubmissionResponseDto> response =
                restTemplate.getForEntity(url + id, SubmissionResponseDto.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            SubmissionResponseDto submissionResponseDto = response.getBody();
            assert submissionResponseDto != null;
            return Optional.of(submissionResponseDto.toSubmission());
        }
        return Optional.empty();
    }
}