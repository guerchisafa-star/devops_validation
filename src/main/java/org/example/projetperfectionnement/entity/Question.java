package org.example.projetperfectionnement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "questions")
@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La question est obligatoire")
    @Column(columnDefinition = "TEXT")
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

    @Enumerated(EnumType.STRING)
    private DifficultyLevel niveau = DifficultyLevel.INTERMEDIAIRE;

    @Enumerated(EnumType.STRING)
    private QuestionCategory category = QuestionCategory.GENERAL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    @JsonIgnore
    private Interview interview;

    public enum DifficultyLevel {
        DEBUTANT, INTERMEDIAIRE, AVANCE
    }

    public enum QuestionCategory {
        FRONTEND, BACKEND, DEVOPS, DATABASE, SECURITY, MOBILE, GENERAL
    }
}
