package nl.hu.inno.hulp.presentation.dtos.response;

import nl.hu.inno.hulp.domain.Review;
import nl.hu.inno.hulp.domain.StudentID;
import nl.hu.inno.hulp.domain.Submission;
import nl.hu.inno.hulp.domain.enums.SubmissionStatus;

import java.util.List;

public record SubmissionDto(
        Long id,
        Long assignmentId,
        StudentID studentId,
        double grade,
        SubmissionStatus status,
        String submittedWorkTypeText,
        Long submissionToPeerReview,
        List<Review> reviews
) {
    public Submission toSubmission() {
        return new Submission(
                this.id,
                this.assignmentId,
                this.studentId,
                this.grade,
                this.status,
                this.submittedWorkTypeText,
                this.submissionToPeerReview,
                this.reviews
        );
    }
}