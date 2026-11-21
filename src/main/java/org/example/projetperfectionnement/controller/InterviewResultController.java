package org.example.projetperfectionnement.controller;

import lombok.RequiredArgsConstructor;
import org.example.projetperfectionnement.dto.InterviewResultRequest;
import org.example.projetperfectionnement.dto.InterviewResultResponse;
import org.example.projetperfectionnement.entity.Interview;
import org.example.projetperfectionnement.entity.InterviewResult;
import org.example.projetperfectionnement.service.InterviewResultService;
import org.example.projetperfectionnement.service.InterviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class InterviewResultController {

    private final InterviewResultService resultService;
    private final InterviewService interviewService;

    @PostMapping
    public ResponseEntity<InterviewResultResponse> createResult(@RequestBody InterviewResultRequest request) {
        try {
            InterviewResult result = request.toEntity();
            
            // Associer l'entretien si l'ID est fourni
            if (request.getInterview() != null && request.getInterview().getId() != null) {
                Optional<Interview> interview = interviewService.getInterviewEntityById(request.getInterview().getId());
                if (interview.isPresent()) {
                    result.setInterview(interview.get());
                }
            }
            
            InterviewResult savedResult = resultService.saveResult(result);
            return ResponseEntity.ok(InterviewResultResponse.fromEntity(savedResult));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<InterviewResultResponse>> getAllResults() {
        try {
            List<InterviewResult> results = resultService.getAllResults();
            List<InterviewResultResponse> responses = results.stream()
                    .map(InterviewResultResponse::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/candidat/{nom}")
    public ResponseEntity<List<InterviewResultResponse>> getResultsByNom(@PathVariable String nom) {
        try {
            List<InterviewResult> results = resultService.getResultsByNomCandidat(nom);
            List<InterviewResultResponse> responses = results.stream()
                    .map(InterviewResultResponse::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/poste/{poste}")
    public ResponseEntity<List<InterviewResultResponse>> getResultsByPoste(@PathVariable String poste) {
        try {
            List<InterviewResult> results = resultService.getResultsByPoste(poste);
            List<InterviewResultResponse> responses = results.stream()
                    .map(InterviewResultResponse::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewResultResponse> getResultById(@PathVariable Long id) {
        try {
            Optional<InterviewResult> result = resultService.getResultById(id);
            if (result.isPresent()) {
                return ResponseEntity.ok(InterviewResultResponse.fromEntity(result.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        try {
            resultService.deleteResult(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
