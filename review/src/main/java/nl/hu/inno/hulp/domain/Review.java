package nl.hu.inno.hulp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import nl.hu.inno.hulp.domain.enums.ReviewType;

@Entity
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    private String feedback;
    private StudentID reviewedBy;
    private ReviewType reviewType;

    public Review(String feedback, StudentID reviewedBy, ReviewType reviewType) {
        this.feedback = feedback;
        this.reviewedBy = reviewedBy;
        this.reviewType = reviewType;
    }

    public Review() {}

    public Long getId() {
        return id;
    }

    public String getFeedback() {
        return feedback;
    }

    public StudentID getReviewedBy() {
        return reviewedBy;
    }

    public ReviewType getReviewType() {
        return reviewType;
    }
}