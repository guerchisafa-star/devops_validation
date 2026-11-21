package org.example.projetperfectionnement.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "interview_results")
@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du candidat est obligatoire")
    private String nomCandidat;

    @NotBlank(message = "Le poste est obligatoire")
    private String poste;

    @NotNull(message = "Le score est obligatoire")
    @Min(value = 0, message = "Le score minimum est 0")
    private Integer score;

    @NotNull(message = "Le pourcentage est obligatoire")
    @Min(value = 0, message = "Le pourcentage minimum est 0")
    @Max(value = 100, message = "Le pourcentage maximum est 100")
    private Double pourcentage;

    @Enumerated(EnumType.STRING)
    private Decision decision;

    @Column(name = "date_passage")
    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "interview_id")
    @JsonIgnoreProperties({"questions", "results"})
    private Interview interview;

    private Integer totalQuestions;
    private Integer bonnesReponses;
    private Integer dureePassage; // en minutes

    public enum Decision {
        ACCEPTE, REFUSE, EN_ATTENTE
    }
}
