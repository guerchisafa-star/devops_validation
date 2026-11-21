package org.example.projetperfectionnement.repository;

import org.example.projetperfectionnement.entity.Interview;
import org.example.projetperfectionnement.entity.InterviewResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewResultRepository extends JpaRepository <InterviewResult, Long> {
    List<InterviewResult> findByNomCandidat(String nomCandidat);
    List<InterviewResult> findByPoste(String poste);
    long countByDecision(InterviewResult.Decision decision);
    void deleteByInterview(Interview interview);
}
