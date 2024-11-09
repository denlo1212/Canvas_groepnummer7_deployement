package nl.hu.inno.hulp.api;

import nl.hu.inno.hulp.domain.Course;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class RestCourse implements ReadOnlyCourse {
    private final RestClient restClient = RestClient.create();
    @Value("${usermoduleurl}")
    private String urlBase;

//    Note: hij mapped automatisch dus vind ik het niet nodig om een converter te bouwen voor de DTO die gestuurd word
    @Override
    public Optional<Course> findById(String courseId) {
        try {
            String url = urlBase + "/course/" + courseId;
            ResponseEntity<Course> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .toEntity(Course.class);

            if (checkStatusAndPayload(response)) {
                return Optional.of(response.getBody());
            }

            else{
                return Optional.empty();
            }

        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private boolean checkStatusAndPayload(ResponseEntity<Course> response){
        return response.getStatusCode().is2xxSuccessful() && response.getBody() != null;
    }
}
