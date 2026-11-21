package org.example.projetperfectionnement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projetperfectionnement.dto.InterviewCreateRequest;
import org.example.projetperfectionnement.dto.InterviewResponse;
import org.example.projetperfectionnement.entity.Interview;
import org.example.projetperfectionnement.exception.ResourceNotFoundException;
import org.example.projetperfectionnement.repository.InterviewRepository;
import org.example.projetperfectionnement.repository.InterviewResultRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final InterviewResultRepository interviewResultRepository;

    public InterviewResponse createInterview(InterviewCreateRequest request) {
        log.info("Création d'un nouvel entretien pour: {}", request.getNomCandidat());
        
        Interview interview = new Interview();
        interview.setNomCandidat(request.getNomCandidat());
        interview.setPrenomCandidat(request.getPrenomCandidat());
        interview.setEmailCandidat(request.getEmailCandidat());
        interview.setPoste(request.getPoste());
        interview.setDuree(request.getDuree());
        interview.setNombreQuestions(request.getNombreQuestions());
        interview.setDifficulty(request.getDifficulty());
        interview.setStatus(Interview.InterviewStatus.BROUILLON);
        interview.setDateCreation(LocalDateTime.now());
        
        Interview savedInterview = interviewRepository.save(interview);
        
        log.info("Entretien créé avec l'ID: {}", savedInterview.getId());
        return mapToResponse(savedInterview);
    }

    public List<InterviewResponse> getAllInterviews() {
        log.info("Récupération de tous les entretiens");
        return interviewRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<InterviewResponse> getInterviewsByPoste(String poste) {
        log.info("Récupération des entretiens pour le poste: {}", poste);
        return interviewRepository.findByPoste(poste)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public InterviewResponse getInterviewById(Long id) {
        log.info("Récupération de l'entretien avec l'ID: {}", id);
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entretien non trouvé avec l'ID: " + id));
        return mapToResponse(interview);
    }

    public java.util.Optional<Interview> getInterviewEntityById(Long id) {
        log.info("Récupération de l'entité entretien avec l'ID: {}", id);
        return interviewRepository.findById(id);
    }

    public InterviewResponse updateInterviewStatus(Long id, Interview.InterviewStatus status) {
        log.info("Mise à jour du statut de l'entretien {} vers: {}", id, status);
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entretien non trouvé avec l'ID: " + id));
        
        interview.setStatus(status);
        Interview updatedInterview = interviewRepository.save(interview);
        
        return mapToResponse(updatedInterview);
    }

    @org.springframework.transaction.annotation.Transactional
    public void deleteInterview(Long id) {
        log.info("Suppression de l'entretien avec l'ID: {}", id);
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entretien non trouvé avec l'ID: " + id));
        interviewResultRepository.deleteByInterview(interview);
        interviewRepository.deleteById(id);
    }

    private InterviewResponse mapToResponse(Interview interview) {
        InterviewResponse response = new InterviewResponse();
        response.setId(interview.getId());
        response.setNomCandidat(interview.getNomCandidat());
        response.setPrenomCandidat(interview.getPrenomCandidat());
        response.setEmailCandidat(interview.getEmailCandidat());
        response.setPoste(interview.getPoste());
        response.setDuree(interview.getDuree());
        response.setNombreQuestions(interview.getNombreQuestions());
        response.setStatus(interview.getStatus());
        response.setDifficulty(interview.getDifficulty());
        response.setDateCreation(interview.getDateCreation());
        response.setQuestionsCount(interview.getQuestions() != null ? interview.getQuestions().size() : 0);
        return response;
    }
}
