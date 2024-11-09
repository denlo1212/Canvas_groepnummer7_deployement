package nl.hu.inno.hulp.data;

import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.domain.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubmissionRepository{
    Optional<Submission> findBySubmissionId(Long submissionId);
    void update(Long submissionId, Submission submission) throws NotFoundException;
}