package nl.hu.inno.hulp.presentation.dtos.response;

import nl.hu.inno.hulp.domain.Assignment;
import nl.hu.inno.hulp.domain.Submission;
import nl.hu.inno.hulp.domain.Team;
import nl.hu.inno.hulp.domain.enums.SubmissionType;

import java.util.Date;
import java.util.List;

public record AssignmentResponseDto(
        Long id,
        String title,
        String description,
        Date dueDate,
        double maxGrade,
        SubmissionType submissionType,
        boolean allowPeerReview,
        List<Submission> submissions,
        List<Team> teams
) {
    public static AssignmentResponseDto build(Assignment assignment) {
        return new AssignmentResponseDto(
                assignment.getId(),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getDueDate(),
                assignment.getMaxGrade(),
                assignment.getSubmissionType(),
                assignment.isAllowPeerReview(),
                assignment.getSubmissions(),
                assignment.getTeams()
        );
    }

    public Assignment toAssignment() {
        return new Assignment(
                this.id,
                this.title,
                this.description,
                this.dueDate,
                this.maxGrade,
                this.submissionType,
                this.allowPeerReview,
                this.submissions,
                this.teams
        );
    }
}