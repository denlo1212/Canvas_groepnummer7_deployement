package nl.hu.inno.hulp.domain;

import jakarta.persistence.*;
import nl.hu.inno.hulp.domain.enums.SubmissionType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Assignment {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    private double maxGrade;
    private SubmissionType submissionType;
    private boolean allowPeerReview;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Submission> submissions = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Team> teams = new ArrayList<>();

    public Assignment() {}

    public Assignment(
            Long id,
            String title,
            String description,
            Date dueDate,
            double maxGrade,
            SubmissionType submissionType,
            boolean allowPeerReview,
            List<Submission> submissions,
            List<Team> teams) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.maxGrade = maxGrade;
        this.submissionType = submissionType;
        this.allowPeerReview = allowPeerReview;
        this.submissions = submissions;
        this.teams = teams;
    }

    public void submitWork(Submission submission) {
        if (isSubmissionAllowed()) {
            submissions.add(submission);
        } else {
            throw new IllegalArgumentException("Cannot submit work after the due date.");
        }
    }

    private boolean isSubmissionAllowed() {
        return new Date().before(dueDate);
    }

    public SubmissionType getSubmissionType() {
        return submissionType;
    }

    public Long getId() {
        return id;
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

    public boolean isAllowPeerReview() {
        return allowPeerReview;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }
    public List<Team> getTeams() {
        return teams;
    }
}