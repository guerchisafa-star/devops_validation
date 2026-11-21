package org.example.projetperfectionnement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.projetperfectionnement.entity.InterviewResult;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResultResponse {
    private Long id;
    private String nomCandidat;
    private String poste;
    private Integer score;
    private Double pourcentage;
    private String decision;
    private LocalDateTime date;
    private Integer totalQuestions;
    private Integer bonnesReponses;
    private Integer dureePassage;
    private InterviewInfo interview;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InterviewInfo {
        private Long id;
        private String nomCandidat;
        private String poste;
    }

    public static InterviewResultResponse fromEntity(InterviewResult entity) {
        InterviewResultResponse response = new InterviewResultResponse();
        response.setId(entity.getId());
        response.setNomCandidat(entity.getNomCandidat());
        response.setPoste(entity.getPoste());
        response.setScore(entity.getScore());
        response.setPourcentage(entity.getPourcentage());
        response.setDecision(entity.getDecision() != null ? entity.getDecision().name() : "EN_ATTENTE");
        response.setDate(entity.getDate());
        response.setTotalQuestions(entity.getTotalQuestions());
        response.setBonnesReponses(entity.getBonnesReponses());
        response.setDureePassage(entity.getDureePassage());
        
        if (entity.getInterview() != null) {
            InterviewInfo interviewInfo = new InterviewInfo();
            interviewInfo.setId(entity.getInterview().getId());
            interviewInfo.setNomCandidat(entity.getInterview().getNomCandidat());
            interviewInfo.setPoste(entity.getInterview().getPoste());
            response.setInterview(interviewInfo);
        }
        
        return response;
    }
}