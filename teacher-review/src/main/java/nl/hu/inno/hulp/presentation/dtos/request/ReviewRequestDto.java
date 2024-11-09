package nl.hu.inno.hulp.presentation.dtos.request;

import nl.hu.inno.hulp.domain.enums.ReviewType;

public class ReviewRequestDto {
    private String feedback;
    private long reviewedBy;
    private ReviewType reviewType;


    private double score;

    public ReviewRequestDto(String feedback, long reviewedBy, ReviewType reviewType, double score) {
        this.feedback = feedback;
        this.reviewedBy = reviewedBy;
        this.reviewType = reviewType;
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public long getReviewedBy() {
        return reviewedBy;
    }

    public ReviewType getReviewType() {
        return reviewType;
    }

    public double getScore() {
        return score;
    }


}