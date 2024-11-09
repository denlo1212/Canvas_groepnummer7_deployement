package nl.hu.inno.hulp.data;

import nl.hu.inno.hulp.domain.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findById(Long submissionId);
}