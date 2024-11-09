package nl.hu.inno.hulp.api.rpc.submission;

import nl.hu.inno.hulp.domain.Submission;

import java.util.Optional;

public interface ReadOnlySubmissionRepository {
    Optional<Submission> findById(Long id);
}
