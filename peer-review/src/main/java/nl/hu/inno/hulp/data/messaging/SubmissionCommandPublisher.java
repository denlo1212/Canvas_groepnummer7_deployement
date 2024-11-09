package nl.hu.inno.hulp.data.messaging;

import nl.hu.inno.hulp.domain.Submission;

public interface SubmissionCommandPublisher {
    void update(Submission submission);
}
