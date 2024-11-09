package nl.hu.inno.hulp.presentation.controller;

import nl.hu.inno.hulp.application.SubmissionService;
import nl.hu.inno.hulp.application.excpetions.NotFoundException;
import nl.hu.inno.hulp.domain.StudentID;
import nl.hu.inno.hulp.presentation.dtos.request.SubmissionRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.SubmissionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/submission")
public class SubmissionController {
    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping()
    public ResponseEntity<SubmissionResponseDto> submitAssignment(@RequestBody SubmissionRequestDto submissionRequestDto) {
        try {
            SubmissionResponseDto submissionResponseDto = submissionService.createSubmission(submissionRequestDto);
            return ResponseEntity.ok(submissionResponseDto);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/{submissionId}/approve/student/{studentId}")
    public ResponseEntity<String> approveSubmission(@PathVariable Long submissionId, @PathVariable StudentID studentId) {
        submissionService.updateTeamSubmission(submissionId, studentId);
        return ResponseEntity.ok("Submission approved by student with ID: " + studentId);
    }

    @GetMapping("/{submissionId}/is-approved")
    public ResponseEntity<Boolean> isSubmissionApprovedByAll(@PathVariable Long submissionId) {
        boolean isApproved = submissionService.isSubmissionApprovedByAll(submissionId);
        return ResponseEntity.ok(isApproved);
    }

    @GetMapping("/{id}")
    public SubmissionResponseDto getSubmissionById(@PathVariable long id) {
        try {
            return submissionService.getById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public SubmissionResponseDto updateSubmission(@PathVariable long id, @RequestBody SubmissionRequestDto submissionRequestDto) {
        try {
            return submissionService.updateSubmission(id, submissionRequestDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}