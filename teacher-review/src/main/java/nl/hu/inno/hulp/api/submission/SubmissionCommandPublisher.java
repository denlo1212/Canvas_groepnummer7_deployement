package nl.hu.inno.hulp.api.submission;

import nl.hu.inno.hulp.domain.Submission;

public interface SubmissionCommandPublisher {
    void update(Submission submission);
}
