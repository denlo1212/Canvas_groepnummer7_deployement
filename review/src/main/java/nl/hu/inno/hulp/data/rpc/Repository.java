package nl.hu.inno.hulp.data.rpc;

import nl.hu.inno.hulp.domain.Assignment;
import nl.hu.inno.hulp.domain.Submission;
import java.util.Optional;

public class Repository implements ReadOnlyRepository {
    private final RestRepository source;

    public Repository(RestRepository source) {
        this.source = source;
    }

    @Override
    public Optional<Submission> findSubmissionById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Assignment> findAssignmentById(Long id) {
        return Optional.empty();
    }
}
