package nl.hu.inno.hulp.api.rpc.assignment;

import nl.hu.inno.hulp.domain.Assignment;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Primary
public class AssignmentRepository implements ReadOnlyAssignmentRepository {
    private final RestAssignmentRepository restAssignmentRepository;

    public AssignmentRepository(RestAssignmentRepository restAssignmentRepository) {
        this.restAssignmentRepository = restAssignmentRepository;
    }

    @Override
    public Optional<Assignment> findById(Long id) {
        return restAssignmentRepository.findById(id);
    }
}
