package nl.hu.inno.hulp.api.rpc.assignment;

import nl.hu.inno.hulp.domain.Assignment;

import java.util.Optional;

public interface ReadOnlyAssignmentRepository {
    Optional<Assignment> findById(Long id) ;
}
