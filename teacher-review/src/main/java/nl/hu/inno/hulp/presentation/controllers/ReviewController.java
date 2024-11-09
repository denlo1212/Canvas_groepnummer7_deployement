package nl.hu.inno.hulp.presentation.controllers;

import nl.hu.inno.hulp.application.ReviewService;
import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.presentation.dtos.request.ReviewRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.SubmissionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService service;

    @Autowired
    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @PostMapping("/submission/{submissionId}")
    public SubmissionResponseDto addTeacherReviewToSubmission(
            @PathVariable Long submissionId,
            @RequestBody ReviewRequestDto teacherReview) {
        try {
            System.out.println(submissionId);
            return service.addTeacherReviewToSubmission(submissionId, teacherReview);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}