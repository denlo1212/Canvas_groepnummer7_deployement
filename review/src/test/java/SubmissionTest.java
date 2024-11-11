//import nl.hu.inno.hulp.domain.Review;
//import nl.hu.inno.hulp.domain.Submission;
//import nl.hu.inno.hulp.domain.enums.ReviewType;
//import nl.hu.inno.hulp.domain.enums.SubmissionStatus;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//
//public class SubmissionTest {
//
//    Long studentIdOne = 1L;
//    Long studentIdTwo = 2L;
//    Review review = new Review("This is my feedback", studentIdOne, ReviewType.STUDENT);
//    Submission submission;
//
//
//    @Test
//    void submitPeerReviewTest() {
//        submission.submitPeerReview(review);
//        assertEquals(SubmissionStatus.REVIEWED, submission.getStatus());
//    }
//
//
//    @Test
//    void linkStudentSubmissions() {
//        submission.linkSubmissionToPeerReview(studentIdOne);
//        assertEquals(studentIdOne, submission.getSubmissionToPeerReview());
//    }
//
//
//    @Test
//    void failSubmitPeerReviewTest(){
//        submission.submitPeerReview(review);
//        assertThrows(IllegalArgumentException.class, () ->
//                submission.submitPeerReview(review));
//    }
//
//
//    @Test
//    void failLinkStudentSubmissions() {
//        submission.linkSubmissionToPeerReview(studentIdOne);
//        assertThrows(IllegalArgumentException.class, () ->
//                submission.linkSubmissionToPeerReview(studentIdTwo));
//    }
//
//
//    @BeforeEach
//    void setup() {
//        String submittedWork = "This is my paper on ....";
//        submission = new Submission(1L, studentIdOne, submittedWork);
//    }
//}
