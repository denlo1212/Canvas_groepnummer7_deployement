package nl.hu.inno.hulp.data.rpc;

import nl.hu.inno.hulp.domain.Assignment;
import nl.hu.inno.hulp.domain.Submission;
import java.util.Optional;

public interface ReadOnlyRepository {
    Optional<Submission> findSubmissionById(Long id);
    Optional<Assignment> findAssignmentById(Long id);

}
