package nl.hu.inno.hulp.domain;

import jakarta.persistence.*;
import nl.hu.inno.hulp.domain.enums.AssignmentType;
import nl.hu.inno.hulp.domain.enums.SubmissionStatus;

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

    public Submission(Long assignmentId,
                      StudentID studentId,
                      String submittedWorkTypeText,
                      double grade,
                      SubmissionStatus status,
                      List<Review> reviews,
                      Long submissionToPeerReview,
                      AssignmentType assignmentType) {
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.submittedWorkTypeText = submittedWorkTypeText;
        this.grade = grade;
        this.status = status;
        this.reviews = reviews;
        this.submissionToPeerReview = submissionToPeerReview;
        this.assignmentType = assignmentType;
    }

    public Submission() {}

    public boolean isApprovedBy(Student student){
        return approvedByStudents.contains(student);
    }

    public void addApproval(Student student){
        approvedByStudents.add(student);
    }

    public void linkSubmissionToPeerReview(Long submissionId) {
        this.submissionToPeerReview = submissionId;
    }


    public void submitPeerReview(Review peerReview) {
        this.reviews.add(peerReview);
        this.status = SubmissionStatus.REVIEWED;
    }

    public void addTeacherReview(Review review, double grade) {
        if (this.status == SubmissionStatus.GRADED) {
            throw new IllegalArgumentException("This submission has already been graded.");
        }
        this.grade = grade;
        this.status = SubmissionStatus.GRADED;
        this.reviews.add(review);
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

    public List<Review> getReviews() {
        return reviews;
    }
}