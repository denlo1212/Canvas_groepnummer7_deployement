package nl.hu.inno.hulp.presentation.dtos.response;

import nl.hu.inno.hulp.domain.Review;
import nl.hu.inno.hulp.domain.StudentID;
import nl.hu.inno.hulp.domain.Submission;
import nl.hu.inno.hulp.domain.enums.SubmissionStatus;

import java.util.List;

public record SubmissionResponseDto(
        Long id,
        Long assignmentId,
        StudentID studentId,
        double grade,
        SubmissionStatus status,
        String submittedWorkTypeText,
        Long submissionToPeerReview,
        List<Review> reviews
) {
    public static SubmissionResponseDto build(Submission submission) {
        return new SubmissionResponseDto(
                submission.getId(),
                submission.getAssignmentId(),
                submission.getStudentId(),
                submission.getGrade(),
                submission.getStatus(),
                submission.getSubmittedWorkTypeText(),
                submission.getSubmissionToPeerReview(),
                submission.getReviews()
        );
    }
}