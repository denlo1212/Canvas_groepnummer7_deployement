package nl.hu.inno.hulp.api.rpc.assignment;

import nl.hu.inno.hulp.domain.Assignment;
import nl.hu.inno.hulp.presentation.dtos.response.AssignmentResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class RestAssignmentRepository implements ReadOnlyAssignmentRepository {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<Assignment> findById(Long id) {
        String url = "http://localhost:8070/assignments/";
        ResponseEntity<AssignmentResponseDto> response =
                restTemplate.getForEntity(url + id, AssignmentResponseDto.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            AssignmentResponseDto assignmentResponseDto = response.getBody();
            assert assignmentResponseDto != null;
            return Optional.of(assignmentResponseDto.toAssignment());
        }
        return Optional.empty();
    }
}