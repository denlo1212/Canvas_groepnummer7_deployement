package nl.hu.inno.hulp.application.services;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.data.rpc.ReadOnlyRepository;
import nl.hu.inno.hulp.data.messaging.SubmissionCommandPublisher;
import nl.hu.inno.hulp.application.exceptions.AssignmentNotFoundException;
import nl.hu.inno.hulp.application.exceptions.PeerReviewNotAllowedException;
import nl.hu.inno.hulp.application.exceptions.SubmissionNotFoundException;
import nl.hu.inno.hulp.domain.*;
import nl.hu.inno.hulp.logging.EventLogger;
import nl.hu.inno.hulp.presentation.dtos.request.InitPeerReviewRequestDto;
import nl.hu.inno.hulp.presentation.dtos.request.ReviewRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.SubmissionResponseDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class PeerReviewService {
    private final ReadOnlyRepository readOnlyRepository;
    private final SubmissionCommandPublisher submissionCommandPublisher;

    public PeerReviewService(
            ReadOnlyRepository readOnlyRepository,
            SubmissionCommandPublisher submissionCommandPublisher) {
        this.readOnlyRepository = readOnlyRepository;
        this.submissionCommandPublisher = submissionCommandPublisher;
    }

    /**
     * This will set up the pair of students
     * to peer-review one another
     */
    public List<SubmissionResponseDto> initPeerReview(InitPeerReviewRequestDto requestDto)
            throws SubmissionNotFoundException, PeerReviewNotAllowedException, AssignmentNotFoundException {
        Assignment assignment = findAssignment(requestDto.getAssignmentId());

        if (!assignment.isAllowPeerReview()) {
            throw new PeerReviewNotAllowedException("This assignment does not allow peer reviews.");
        }

        return createPeerReviewPair(
                assignment.getSubmissions(),
                requestDto.getStudentOneId(),
                requestDto.getStudentTwoId()
        );
    }

    /**
     * This method will submit a peer review to a submission,
     * and update that submission object.
     */
    public SubmissionResponseDto submitPeerReview(Long submissionId, ReviewRequestDto reviewData) throws SubmissionNotFoundException, PeerReviewNotAllowedException {
        // Find the submission & submit the peer review to it.
        Submission submission = findSubmission(submissionId);

        Review review = new Review(
                reviewData.getFeedback(),
                reviewData.getReviewedBy(),
                reviewData.getReviewType()
        );
        submission.submitPeerReview(review);

        // Publish a COMMAND to update the submission.
        submissionCommandPublisher.update(submission);
        return SubmissionResponseDto.build(submission);
    }


    /**
     * This method will create a peer review pair as a value object
     *
     * @param submissions
     * @param studentIdOne
     * @param studentIdTwo
     * @return A list of the two submissions as DTOs
     */
    private List<SubmissionResponseDto> createPeerReviewPair(List<Submission> submissions, StudentID studentIdOne, StudentID studentIdTwo)
            throws SubmissionNotFoundException, PeerReviewNotAllowedException {

        PeerReviewPair peerReviewPair = new PeerReviewPair(submissions, studentIdOne, studentIdTwo);
        EventLogger.info("Successful peer review pair creation");

        // Publish a COMMAND to update the submission.
        submissionCommandPublisher.update(peerReviewPair.getSubmissionStudentOne());
        submissionCommandPublisher.update(peerReviewPair.getSubmissionStudentTwo());
        return List.of(
                SubmissionResponseDto.build(peerReviewPair.getSubmissionStudentOne()),
                SubmissionResponseDto.build(peerReviewPair.getSubmissionStudentTwo())
        );
    }



    private Assignment findAssignment(Long id) throws AssignmentNotFoundException {
        return readOnlyRepository.findAssignmentById(id).
                orElseThrow(() -> new AssignmentNotFoundException("Could not find assignment with id: " + id));
    }

    private Submission findSubmission(Long id) throws SubmissionNotFoundException {
        return readOnlyRepository.findSubmissionById(id)
                .orElseThrow(() -> new SubmissionNotFoundException("Could not find submission with id: " + id));
    }
}
