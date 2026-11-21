package org.example.projetperfectionnement.service;

import lombok.RequiredArgsConstructor;
import org.example.projetperfectionnement.dto.DashboardStatsResponse;
import org.example.projetperfectionnement.entity.Interview;
import org.example.projetperfectionnement.entity.InterviewResult;
import org.example.projetperfectionnement.repository.InterviewRepository;
import org.example.projetperfectionnement.repository.InterviewResultRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final InterviewRepository interviewRepository;
    private final InterviewResultRepository resultRepository;

    public DashboardStatsResponse getDashboardStats() {
        // Compter les entretiens
        long totalInterviews = interviewRepository.count();
        
        // Compter les entretiens d'aujourd'hui
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        long todayInterviews = interviewRepository.countByDateCreationBetween(startOfDay, endOfDay);
        
        // Statistiques des résultats
        long totalResults = resultRepository.count();
        long acceptedResults = resultRepository.countByDecision(InterviewResult.Decision.ACCEPTE);
        long rejectedResults = resultRepository.countByDecision(InterviewResult.Decision.REFUSE);
        
        // Calculer le taux de réussite
        double passRate = totalResults > 0 ? (double) acceptedResults / totalResults * 100 : 0;
        
        // Calculer le score moyen
        List<InterviewResult> results = resultRepository.findAll();
        double averageScore = results.stream()
                .mapToDouble(InterviewResult::getPourcentage)
                .average()
                .orElse(0.0);

        return new DashboardStatsResponse(
                totalInterviews,
                averageScore,
                passRate,
                todayInterviews,
                totalResults,
                acceptedResults,
                rejectedResults
        );
    }
}