package nl.hu.inno.hulp.application;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.application.excpetions.NotFoundException;
import nl.hu.inno.hulp.data.*;
import nl.hu.inno.hulp.domain.*;
import nl.hu.inno.hulp.presentation.dtos.request.SubmissionRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.SubmissionResponseDto;
import org.springframework.stereotype.Service;

import java.util.Date;


@Transactional
@Service
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;
    private final TeamRepository teamRepository;
    private final StaticData staticData = new StaticData();

    public SubmissionService(SubmissionRepository submissionRepository, StudentRepository studentRepository, AssignmentRepository assignmentRepository, TeamRepository teamRepository) throws NotFoundException {
        this.submissionRepository = submissionRepository;
        this.studentRepository = studentRepository;
        this.assignmentRepository = assignmentRepository;
        this.teamRepository = teamRepository;
        this.submissionRepository.saveAll(staticData.getSubmissions());
    }


    public SubmissionResponseDto createSubmission(SubmissionRequestDto submissionRequestDto) {
        Student student = studentRepository.findByStudentID(submissionRequestDto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("No student found with id: " + submissionRequestDto.getStudentId()));

        Assignment assignment = assignmentRepository.findById(submissionRequestDto.getAssignmentId())
                .orElseThrow(() -> new IllegalArgumentException("No assignment found with id: " + submissionRequestDto.getAssignmentId()));

        if (assignment.getDueDate().before(new Date())) {
            throw new IllegalArgumentException("Assignment deadline has passed.");
        }

        Submission newSubmission = new Submission(submissionRequestDto.getAssignmentId(), submissionRequestDto.getStudentId(), submissionRequestDto.getSubmission());

        if (submissionRequestDto.getTeamId() != null) {
            Team team = teamRepository.findByTeamID(submissionRequestDto.getTeamId())
                    .orElseThrow(() -> new IllegalArgumentException("No team found with id: " + submissionRequestDto.getTeamId()));
        }

        submissionRepository.save(newSubmission);
        return SubmissionResponseDto.build(newSubmission);
    }


    public void updateTeamSubmission(Long submissionId, StudentID studentId){
        Student student = studentRepository.findByStudentID(studentId)
                .orElseThrow(() -> new IllegalArgumentException("No student found with id: " + studentId));

        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("No submission found with id: " + submissionId));

        submission.addApproval(student);
        submissionRepository.save(submission);
    }

    public boolean isSubmissionApprovedByAll(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("No submission found with id: " + submissionId));

        Assignment assignment = assignmentRepository.findById(submission.getAssignmentId())
                .orElseThrow(() -> new IllegalArgumentException("No assignment found with id: " + submission.getAssignmentId()));

        if (submission.getTeam() == null) {
            throw new IllegalArgumentException("No team associated with this submission");
        }

        if (!assignment.getTeams().contains(submission.getTeam())) {
            throw new IllegalArgumentException("Team associated with this submission is not part of the assignment");
        }

        return submission.getTeam().allMembersApproved(submission);
    }

    public SubmissionResponseDto getById(long id) throws NotFoundException {
        Submission submission = submissionRepository.findById(id).orElseThrow(() -> new NotFoundException("Submission not found"));
        return SubmissionResponseDto.build(submission);
    }

    public SubmissionResponseDto updateSubmission(Long id, SubmissionRequestDto studentRequestDto) throws NotFoundException {
        Submission submission = submissionRepository.findById(id).orElseThrow(() -> new NotFoundException("Submission not found"));
        submission.updateSubmission(studentRequestDto);
        submissionRepository.save(submission);
        return SubmissionResponseDto.build(submission);
    }
}