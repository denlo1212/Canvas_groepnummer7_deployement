package nl.hu.inno.hulp.domain;

import jakarta.persistence.*;
import nl.hu.inno.hulp.application.exceptions.PeerReviewNotAllowedException;
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
    @Embedded
    @AttributeOverride(name = "studentNumber", column = @Column(name = "student_number"))
    private StudentID studentId;
    private double grade;
    private SubmissionStatus status;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Review> reviews;
    private String submittedWorkTypeText;
    private Long submissionToPeerReview;
    @Embedded
    @AttributeOverride(name = "studentNumber", column = @Column(name = "reviewer_student_number"))
    private StudentID studentReviewer;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Student> approvedByStudents = new HashSet<>();
    private AssignmentType assignmentType;


    public Submission(Long assignmentId, StudentID studentId, String submittedWorkTypeText) {
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.submittedWorkTypeText = submittedWorkTypeText;
        this.grade = 0.0;
        this.status = SubmissionStatus.PENDING;
        this.reviews = new ArrayList<>();
        this.submissionToPeerReview = null;
        this.studentReviewer = null;
        this.assignmentType = null;
    }

    public Submission() {}

    public Submission(
            Long id,
            Long assignmentId,
            StudentID studentId,
            String submittedWorkTypeText,
            double grade,
            SubmissionStatus status,
            List<Review> reviews,
            Long submissionToPeerReview
    ) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.submittedWorkTypeText = submittedWorkTypeText;
        this.grade = grade;
        this.status = status;
        this.reviews = (reviews != null) ? reviews : new ArrayList<>();
        this.submissionToPeerReview = submissionToPeerReview;
    }

    public void submitPeerReview(Review review) throws PeerReviewNotAllowedException {
        if (submissionToPeerReview == null) {
            throw new PeerReviewNotAllowedException("This submission can not be reviewed by a student.");
        }
        if (status == SubmissionStatus.GRADED) {
            throw new PeerReviewNotAllowedException("This submission can no longer be reviewed. As it has already been graded.");
        }
        if (status == SubmissionStatus.PENDING || status == SubmissionStatus.REVIEWED) {
            status = SubmissionStatus.REVIEWED;
            this.reviews.add(review);
        }
    }

    public void linkSubmissionToPeerReview(Long submissionId) throws PeerReviewNotAllowedException {
        if (submissionToPeerReview == null) {
            submissionToPeerReview = submissionId;
        }
        else {
            throw new PeerReviewNotAllowedException("Another student is already assigned to review this submission.");
        }
    }

    public void addTeacherReview(Review review, double grade) {
        if (this.status == SubmissionStatus.GRADED) {
            throw new IllegalArgumentException("This submission has already been graded.");
        }
        this.grade = grade;
        this.status = SubmissionStatus.GRADED;
        this.reviews.add(review);
    }

    public boolean isApprovedBy(Student student){
        return approvedByStudents.contains(student);
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public double getGrade() {
        return grade;
    }

    public Long getSubmissionToPeerReview() {
        return submissionToPeerReview;
    }

    public String getSubmittedWorkTypeText() {
        return submittedWorkTypeText;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public Long getId() {
        return id;
    }

    public StudentID getStudentId() {
        return studentId;
    }
    public void setId(Long id) {
        this.id = id;
    }
}