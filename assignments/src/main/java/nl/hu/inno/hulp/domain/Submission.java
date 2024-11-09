package nl.hu.inno.hulp.domain;

import jakarta.persistence.*;
import nl.hu.inno.hulp.domain.enums.SubmissionStatus;
import nl.hu.inno.hulp.domain.enums.AssignmentType;
import nl.hu.inno.hulp.presentation.dtos.request.SubmissionRequestDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Submission {
    @Id
    @GeneratedValue
    private Long id;
    private Long assignmentId;
    private StudentID studentId;
    private double grade;
    private SubmissionStatus status;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
    private String submittedWorkTypeText;
    private Long submissionToPeerReview;
    private AssignmentType assignmentType;
    @ManyToMany
    private Set<Student> approvedByStudents = new HashSet<>();
    @ManyToOne
    private Team team;

    public Submission(Long assignmentId, StudentID studentId, String submittedWorkTypeText) {
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.submittedWorkTypeText = submittedWorkTypeText;
        this.grade = 0.0;
        this.status = SubmissionStatus.PENDING;
        this.reviews = new ArrayList<>();
        this.submissionToPeerReview = null;
        this.assignmentType = null;
    }

    public Submission() {}

    public Submission(Long id, Long assignmentId, StudentID studentId, double grade, SubmissionStatus status, String submittedWorkTypeText, Long submissionToPeerReview, List<Review> reviews) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.grade = grade;
        this.status = status;
        this.submissionToPeerReview = submissionToPeerReview;
        this.submittedWorkTypeText = submittedWorkTypeText;
        this.reviews = reviews;
    }

    public boolean isApprovedBy(Student student){
        return approvedByStudents.contains(student);
    }

    public void addApproval(Student student){
        approvedByStudents.add(student);
    }


    public void addTeacherReview(Review review, double grade) {
        if (this.status == SubmissionStatus.REVIEWED) {
            throw new IllegalArgumentException("This submission has already been reviewed.");
        }
        this.reviews.add(review);
        this.grade = grade;
        this.status = SubmissionStatus.REVIEWED;
    }

    public void updateSubmission(SubmissionRequestDto submissionRequestDto) {

    }

    public SubmissionStatus getStatus() {
        return status;
    }


    public AssignmentType getAssignmentType() {
        return assignmentType;
    }


    public double getGrade() {
        return grade;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<Student> getApprovedByStudents() {
        return approvedByStudents;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public Team getTeam() {
        return team;
    }

    public Long getSubmissionToPeerReview() {
        return submissionToPeerReview;
    }

    public String getSubmittedWorkTypeText() {
        return submittedWorkTypeText;
    }

    public StudentID getStudentId() {
        return studentId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public Long getId() {
        return id;
    }
}