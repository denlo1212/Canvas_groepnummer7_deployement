package nl.hu.inno.hulp.domain;


import nl.hu.inno.hulp.application.exceptions.PeerReviewNotAllowedException;
import nl.hu.inno.hulp.application.exceptions.SubmissionNotFoundException;
import java.util.List;
import java.util.Optional;

public class PeerReviewPair {
    private final List<Submission> submissions;
    private final StudentID studentIdOne;
    private final StudentID studentIdTwo;
    private Submission submissionStudentOne = null;
    private Submission submissionStudentTwo = null;

    public PeerReviewPair(List<Submission> submissions, StudentID studentIdOne, StudentID studentIdTwo) throws SubmissionNotFoundException, PeerReviewNotAllowedException {
        this.submissions = submissions;
        this.studentIdOne = studentIdOne;
        this.studentIdTwo = studentIdTwo;
        this.initializeSubmissions();
    }

    /**
     * Finds and initializes the submissions for the given student IDs.
     * @throws SubmissionNotFoundException if one or both submissions are not found.
     */
    private void initializeSubmissions() throws SubmissionNotFoundException, PeerReviewNotAllowedException {
        submissionStudentOne = findSubmissionByStudentId(studentIdOne)
                .orElseThrow(() -> new SubmissionNotFoundException("Submission not found for student ID: " + studentIdOne));
        submissionStudentTwo = findSubmissionByStudentId(studentIdTwo)
                .orElseThrow(() -> new SubmissionNotFoundException("Submission not found for student ID: " + studentIdTwo));

        linkStudents();
    }

    /**
     * Finds a submission by student ID.
     * @param studentId The ID of the student whose submission is to be found.
     * @return An Optional containing the found submission, or empty if not found.
     */
    private Optional<Submission> findSubmissionByStudentId(StudentID studentId) {
        return submissions.stream()
                .filter(submission -> studentId.equals(submission.getStudentId()))
                .findFirst();
    }

    /**
     * Links two submissions to each other for peer review.
     */
    private void linkStudents() throws PeerReviewNotAllowedException {
        submissionStudentOne.linkSubmissionToPeerReview(submissionStudentTwo.getId());
        submissionStudentTwo.linkSubmissionToPeerReview(submissionStudentOne.getId());
    }

    public Submission getSubmissionStudentOne() {
        return submissionStudentOne;
    }

    public Submission getSubmissionStudentTwo() {
        return submissionStudentTwo;
    }
}