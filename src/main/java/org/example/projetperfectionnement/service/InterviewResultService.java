package org.example.projetperfectionnement.service;

import lombok.RequiredArgsConstructor;
import org.example.projetperfectionnement.entity.InterviewResult;
import org.example.projetperfectionnement.repository.InterviewResultRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterviewResultService {

    private final InterviewResultRepository resultRepository;


    public InterviewResult saveResult(InterviewResult result) {
        result.setDate(LocalDateTime.now());
        return resultRepository.save(result);
    }


    public List<InterviewResult> getAllResults() {
        return resultRepository.findAll();
    }


    public List<InterviewResult> getResultsByNomCandidat(String nomCandidat) {
        return resultRepository.findByNomCandidat(nomCandidat);
    }


    public List<InterviewResult> getResultsByPoste(String poste) {
        return resultRepository.findByPoste(poste);
    }


    public Optional<InterviewResult> getResultById(Long id) {
        return resultRepository.findById(id);
    }


    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }
}
