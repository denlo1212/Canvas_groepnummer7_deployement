package nl.hu.inno.hulp.application;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.data.SubmissionRepository;
import nl.hu.inno.hulp.domain.Review;
import nl.hu.inno.hulp.domain.Submission;
import nl.hu.inno.hulp.presentation.dtos.request.ReviewRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.SubmissionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReviewService {
    private final SubmissionRepository submissionRepository;

    @Autowired
    public ReviewService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    private Review dtoToObject(ReviewRequestDto requestDto) {
        return new Review(
                requestDto.getFeedback(),
                requestDto.getReviewedBy(),
                requestDto.getReviewType()
        );
    }

    public SubmissionResponseDto addTeacherReviewToSubmission(Long submissionId, ReviewRequestDto requestDto) throws NotFoundException {
        Submission submission = submissionRepository.findBySubmissionId(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("No submission found with id: " + submissionId));

        System.out.println(submission);
        submission.addTeacherReview(dtoToObject(requestDto), requestDto.getScore());
        submissionRepository.update(submission.getId(), submission);
        return SubmissionResponseDto.build(submission);
    }

}