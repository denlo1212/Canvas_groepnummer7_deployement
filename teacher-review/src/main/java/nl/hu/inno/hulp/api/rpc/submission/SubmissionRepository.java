package nl.hu.inno.hulp.api.rpc.submission;

import nl.hu.inno.hulp.domain.Submission;

import java.util.Optional;

public class SubmissionRepository implements ReadOnlySubmissionRepository {
    private final RestSubmissionRepository restSubmissionRepository;

    public SubmissionRepository(RestSubmissionRepository restSubmissionRepository) {
        this.restSubmissionRepository = restSubmissionRepository;
    }

    @Override
    public Optional<Submission> findById(Long id) {
        return restSubmissionRepository.findById(id);
    }
}
