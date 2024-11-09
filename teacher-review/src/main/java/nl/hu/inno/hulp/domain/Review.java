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
    private Long reviewedBy;
    private ReviewType reviewType;

    public Review(String feedback, Long reviewedBy, ReviewType reviewType) {
        this.feedback = feedback;
        this.reviewedBy = reviewedBy;
        this.reviewType = reviewType;
    }

    public Review() {

    }


}