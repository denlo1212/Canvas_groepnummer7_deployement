package nl.hu.inno.hulp.data;

import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.domain.Submission;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Primary
@Component
public class RestSubmissionRepository implements SubmissionRepository {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<Submission> findBySubmissionId(Long submissionID) {
        try {
            ResponseEntity<Submission> response = restTemplate.getForEntity("http://localhost:8081/submission/" + submissionID, Submission.class);
            return response.getStatusCode().is2xxSuccessful() ? Optional.ofNullable(response.getBody()) : Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    @Override
    public void update(Long submissionID, Submission submissionToUpdate) throws NotFoundException {
        String url = "http://localhost:8081/submission/update/" + submissionID;

        if (submissionToUpdate == null) {
            throw new NotFoundException("Submission with ID " + submissionID + " not found for update");
        }
        restTemplate.put(url, submissionToUpdate);
    }



}