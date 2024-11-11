package nl.hu.inno.hulp.application.services;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.data.messaging.SubmissionCommandPublisher;
import nl.hu.inno.hulp.data.rpc.ReadOnlyRepository;
import nl.hu.inno.hulp.domain.Review;
import nl.hu.inno.hulp.domain.Submission;
import nl.hu.inno.hulp.presentation.dtos.request.ReviewRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.SubmissionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TeacherReviewService {
    private final ReadOnlyRepository readOnlyRepository;
    private final SubmissionCommandPublisher submissionCommandPublisher;

    @Autowired
    public TeacherReviewService(ReadOnlyRepository readOnlyRepository, SubmissionCommandPublisher submissionCommandPublisher) {
        this.readOnlyRepository = readOnlyRepository;
        this.submissionCommandPublisher = submissionCommandPublisher;
    }

    private Review dtoToObject(ReviewRequestDto requestDto) {
        return new Review(
                requestDto.getFeedback(),
                requestDto.getReviewedBy(),
                requestDto.getReviewType()
        );
    }

    public SubmissionResponseDto addTeacherReviewToSubmission(Long submissionId, ReviewRequestDto requestDto) {
        Submission submission = readOnlyRepository.findSubmissionById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("No submission found with id: " + submissionId));

        System.out.println(submission);
        submission.addTeacherReview(dtoToObject(requestDto), requestDto.getScore());
        submissionCommandPublisher.update(submission);
        return SubmissionResponseDto.build(submission);
    }
}