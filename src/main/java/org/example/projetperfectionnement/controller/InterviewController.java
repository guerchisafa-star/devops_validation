package org.example.projetperfectionnement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.projetperfectionnement.dto.InterviewCreateRequest;
import org.example.projetperfectionnement.dto.InterviewResponse;
import org.example.projetperfectionnement.entity.Interview;
import org.example.projetperfectionnement.service.InterviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping
    public ResponseEntity<InterviewResponse> createInterview(@Valid @RequestBody InterviewCreateRequest request) {
        InterviewResponse response = interviewService.createInterview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<InterviewResponse>> getAllInterviews() {
        List<InterviewResponse> interviews = interviewService.getAllInterviews();
        return ResponseEntity.ok(interviews);
    }

    @GetMapping("/poste/{poste}")
    public ResponseEntity<List<InterviewResponse>> getInterviewsByPoste(@PathVariable String poste) {
        List<InterviewResponse> interviews = interviewService.getInterviewsByPoste(poste);
        return ResponseEntity.ok(interviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewResponse> getInterviewById(@PathVariable Long id) {
        InterviewResponse interview = interviewService.getInterviewById(id);
        return ResponseEntity.ok(interview);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<InterviewResponse> updateInterviewStatus(
            @PathVariable Long id, 
            @RequestParam Interview.InterviewStatus status) {
        InterviewResponse interview = interviewService.updateInterviewStatus(id, status);
        return ResponseEntity.ok(interview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {
        interviewService.deleteInterview(id);
        return ResponseEntity.noContent().build();
    }
}
