package nl.hu.inno.hulp.presentation.controllers;


import nl.hu.inno.hulp.application.exceptions.AssignmentNotFoundException;
import nl.hu.inno.hulp.application.exceptions.PeerReviewNotAllowedException;
import nl.hu.inno.hulp.application.exceptions.SubmissionNotFoundException;
import nl.hu.inno.hulp.application.services.PeerReviewService;
import nl.hu.inno.hulp.presentation.dtos.request.InitPeerReviewRequestDto;
import nl.hu.inno.hulp.presentation.dtos.request.ReviewRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.SubmissionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class PeerReviewController {

    private final PeerReviewService service;

    @Autowired
    public PeerReviewController(PeerReviewService service) {
        this.service = service;
    }

    @PostMapping("/initPeer")
    public List<SubmissionResponseDto> initPeerReview(@RequestBody InitPeerReviewRequestDto requestDto)
            throws PeerReviewNotAllowedException, SubmissionNotFoundException, AssignmentNotFoundException {
        return service.initPeerReview(requestDto);
    }

    @PutMapping("/submitPeerReview/{submissionId}")
    public SubmissionResponseDto submitPeerReview(@PathVariable long submissionId, @RequestBody ReviewRequestDto peerReview)
            throws SubmissionNotFoundException, PeerReviewNotAllowedException {
        return service.submitPeerReview(submissionId, peerReview);
    }
}
