package nl.hu.inno.hulp.data;

import nl.hu.inno.hulp.domain.Team;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
@Primary
@Component
public class RestTeamRepository implements TeamRepository{

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<Team> findByTeamID(Long teamID) {
        ResponseEntity<Team> response = restTemplate.getForEntity("http://localhost:8080/teams/" + teamID, Team.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional.of(response.getBody());
        } else {
            return Optional.empty();
        }
    }
}