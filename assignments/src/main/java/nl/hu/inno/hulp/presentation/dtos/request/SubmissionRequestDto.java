package nl.hu.inno.hulp.presentation.dtos.request;

import nl.hu.inno.hulp.domain.StudentID;

public class SubmissionRequestDto {
    private StudentID studentId;
    private String submission;
    private Long assignmentId;
    private Long teamId;


    public SubmissionRequestDto(StudentID studentId, Long assignmentId, Long teamId, String submission){
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.submission = submission;
        this.teamId = teamId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getSubmission() {
        return submission;
    }

    public void setSubmission(String submission) {
        this.submission = submission;
    }

    public StudentID getStudentId() {
        return studentId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setStudentId(StudentID studentId) {
        this.studentId = studentId;
    }
}