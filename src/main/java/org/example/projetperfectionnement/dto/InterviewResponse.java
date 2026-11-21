package org.example.projetperfectionnement.dto;

import lombok.Data;
import org.example.projetperfectionnement.entity.Interview;

import java.time.LocalDateTime;

@Data
public class InterviewResponse {
    private Long id;
    private String nomCandidat;
    private String prenomCandidat;
    private String emailCandidat;
    private String poste;
    private Integer duree;
    private Integer nombreQuestions;
    private Interview.InterviewStatus status;
    private Interview.DifficultyLevel difficulty;
    private LocalDateTime dateCreation;
    private Integer questionsCount;
}