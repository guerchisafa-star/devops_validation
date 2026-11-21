package org.example.projetperfectionnement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.example.projetperfectionnement.entity.Question;

@Data
public class QuestionCreateRequest {
    @NotBlank(message = "La question est obligatoire")
    private String question;

    @NotBlank(message = "Le choix A est obligatoire")
    private String choixA;

    @NotBlank(message = "Le choix B est obligatoire")
    private String choixB;

    @NotBlank(message = "Le choix C est obligatoire")
    private String choixC;

    @NotBlank(message = "Le choix D est obligatoire")
    private String choixD;

    @NotBlank(message = "La bonne réponse est obligatoire")
    @Pattern(regexp = "^[ABCD]$", message = "La bonne réponse doit être A, B, C ou D")
    private String bonneReponse;

    private Question.DifficultyLevel niveau = Question.DifficultyLevel.INTERMEDIAIRE;
    private Question.QuestionCategory category = Question.QuestionCategory.GENERAL;

    @NotNull(message = "L'ID de l'entretien est obligatoire")
    private Long interviewId;
}