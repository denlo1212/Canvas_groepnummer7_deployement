package nl.hu.inno.hulp.presentation.dtos.request;

import nl.hu.inno.hulp.domain.StudentID;

public class InitPeerReviewRequestDto {
    private final StudentID studentOneId;
    private final StudentID studentTwoId;
    private final Long assignmentId;

    public InitPeerReviewRequestDto(StudentID studentOneId, StudentID studentTwoId, Long assignmentId) {
        this.studentOneId = studentOneId;
        this.studentTwoId = studentTwoId;
        this.assignmentId = assignmentId;
    }

    public StudentID getStudentOneId() {
        return studentOneId;
    }

    public StudentID getStudentTwoId() {
        return studentTwoId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }
}