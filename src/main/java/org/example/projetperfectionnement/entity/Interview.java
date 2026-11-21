package org.example.projetperfectionnement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
//annotations lombok
@Entity//classe liee au table
@Getter // creer getter automatiquement
@Setter//creer setter automatiquement
@NoArgsConstructor //creer un constructeur vide
@AllArgsConstructor//creer un constructeurs avec tous les champs
@Table(name = "interviews")
public class Interview {

    @Id
    //id génerer automatiquement dans la base
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//champ obligatoire
    @NotBlank(message = "Le nom du candidat est obligatoire")
    private String nomCandidat;

    private String prenomCandidat;
    private String emailCandidat;
//champ oblifgatoire
    @NotBlank(message = "Le poste est obligatoire")
    private String poste;

    @NotNull(message = "La durée est obligatoire")
    @Min(value = 5, message = "La durée minimum est de 5 minutes")
    private Integer duree;

    @NotNull(message = "Le nombre de questions est obligatoire")
    @Min(value = 1, message = "Il faut au moins 1 question")
    private Integer nombreQuestions;
//ennumeration
    @Enumerated(EnumType.STRING)
    private InterviewStatus status = InterviewStatus.BROUILLON;
////enumeration
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty = DifficultyLevel.INTERMEDIAIRE;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();
//onetomany un interview--->plusieurs quetion
    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Question> questions;
//enumartion=liste des valeurs possibles
    public enum InterviewStatus {
        BROUILLON, PRET, EN_COURS, TERMINE
    }

    public enum DifficultyLevel {
        DEBUTANT, INTERMEDIAIRE, AVANCE
    }
}
