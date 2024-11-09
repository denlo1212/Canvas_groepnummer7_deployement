//import nl.hu.inno.hulp.application.exceptions.SubmissionNotFoundException;
//import nl.hu.inno.hulp.domain.PeerReviewPair;
//import nl.hu.inno.hulp.domain.Submission;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Assertions;
//import org.mockito.Mockito;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//public class PeerReviewPairTest {
//    private List<Submission> submissions;
//    private Submission submissionOne;
//    private Submission submissionTwo;
//    private Submission submissionThree;
//    private final Long studentIdOne = 1L;
//    private final Long studentIdTwo = 2L;
//    private final Long studentIdThree = 3L;
//
//    @BeforeEach
//    public void setUp() {
//        // Mock two Submission objects
//        submissionOne = Mockito.mock(Submission.class);
//        submissionTwo = Mockito.mock(Submission.class);
//        submissionThree = Mockito.mock(Submission.class);
//        submissions = List.of(submissionOne, submissionTwo, submissionThree);
//
//        // Set up mock behavior
//        Mockito.when(submissionOne.getStudentId()).thenReturn(studentIdOne);
//        Mockito.when(submissionTwo.getStudentId()).thenReturn(studentIdTwo);
//        Mockito.when(submissionThree.getStudentId()).thenReturn(studentIdThree);
//
//        Mockito.when(submissionOne.getId()).thenReturn(101L);
//        Mockito.when(submissionTwo.getId()).thenReturn(102L);
//        Mockito.when(submissionThree.getId()).thenReturn(103L);
//    }
//
//    @Test
//    public void testSuccessfulCreation() {
//        Assertions.assertDoesNotThrow(() -> new PeerReviewPair(submissions, studentIdOne, studentIdTwo));
//    }
//
//    @Test
//    public void testSubmissionNotFoundExceptionWhenSubmissionMissing() {
//        assertThrows(SubmissionNotFoundException.class, () ->
//                new PeerReviewPair(submissions, studentIdOne, 4L));
//    }
//}
