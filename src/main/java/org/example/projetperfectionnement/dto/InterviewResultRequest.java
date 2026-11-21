package org.example.projetperfectionnement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.projetperfectionnement.entity.InterviewResult;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResultRequest {
    private String nomCandidat;
    private String poste;
    private Integer score;
    private Double pourcentage;
    private String decision;
    private Integer totalQuestions;
    private Integer bonnesReponses;
    private Integer dureePassage;
    private InterviewRef interview;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InterviewRef {
        private Long id;
    }

    public InterviewResult toEntity() {
        InterviewResult entity = new InterviewResult();
        entity.setNomCandidat(this.nomCandidat);
        entity.setPoste(this.poste);
        entity.setScore(this.score);
        entity.setPourcentage(this.pourcentage);
        
        if (this.decision != null) {
            try {
                entity.setDecision(InterviewResult.Decision.valueOf(this.decision));
            } catch (IllegalArgumentException e) {
                entity.setDecision(InterviewResult.Decision.EN_ATTENTE);
            }
        } else {
            entity.setDecision(InterviewResult.Decision.EN_ATTENTE);
        }
        
        entity.setTotalQuestions(this.totalQuestions);
        entity.setBonnesReponses(this.bonnesReponses);
        entity.setDureePassage(this.dureePassage);
        
        return entity;
    }
}