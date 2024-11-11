package nl.hu.inno.hulp.presentation.controllers;

import nl.hu.inno.hulp.application.services.TeacherReviewService;
import nl.hu.inno.hulp.presentation.dtos.request.ReviewRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.SubmissionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class TeacherReviewController {

    private final TeacherReviewService service;

    @Autowired
    public TeacherReviewController(TeacherReviewService service) {
        this.service = service;
    }

    @PostMapping("/submission/{submissionId}")
    public SubmissionResponseDto addTeacherReviewToSubmission(
            @PathVariable Long submissionId,
            @RequestBody ReviewRequestDto teacherReview) {
        System.out.println(submissionId);
        return service.addTeacherReviewToSubmission(submissionId, teacherReview);
    }
}