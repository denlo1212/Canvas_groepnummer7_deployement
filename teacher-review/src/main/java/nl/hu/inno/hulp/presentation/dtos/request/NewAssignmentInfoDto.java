package nl.hu.inno.hulp.presentation.dtos.request;

import nl.hu.inno.hulp.domain.enums.SubmissionType;

import java.util.Date;

public class NewAssignmentInfoDto {
    private String title;
    private String description;
    private Date dueDate;
    private double maxGrade;
    private SubmissionType submissionType;
    private boolean allowPeerReview;

    public NewAssignmentInfoDto(
            String title,
            String description,
            Date dueDate,
            double maxGrade,
            SubmissionType submissionType,
            boolean allowPeerReview) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.maxGrade = maxGrade;
        this.submissionType = submissionType;
        this.allowPeerReview = allowPeerReview;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public double getMaxGrade() {
        return maxGrade;
    }

    public SubmissionType getSubmissionType() {
        return submissionType;
    }

    public boolean isAllowPeerReview() {
        return allowPeerReview;
    }
}