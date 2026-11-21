package org.example.projetperfectionnement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.projetperfectionnement.entity.Interview;

@Data
public class InterviewCreateRequest {
    @NotBlank(message = "Le nom du candidat est obligatoire")
    private String nomCandidat;

    private String prenomCandidat;
    private String emailCandidat;

    @NotBlank(message = "Le poste est obligatoire")
    private String poste;

    @NotNull(message = "La durée est obligatoire")
    @Min(value = 5, message = "La durée minimum est de 5 minutes")
    private Integer duree;

    @NotNull(message = "Le nombre de questions est obligatoire")
    @Min(value = 1, message = "Il faut au moins 1 question")
    private Integer nombreQuestions;

    private Interview.DifficultyLevel difficulty = Interview.DifficultyLevel.INTERMEDIAIRE;
}