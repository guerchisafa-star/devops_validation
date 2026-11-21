package org.example.projetperfectionnement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
//lombok sert a eviter d'écrire du code répetetif
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//annotations lombok
@Getter //creer automatiquement les getter
@Setter//creer automatiquement les setter
@NoArgsConstructor//creer un constructeur vide
@AllArgsConstructor//creer un constructeutr avec tous les champs
//@Entity classe liée au table
@Entity
@Table(name = "admins")
public class Admin {
    //clé primaire
    @Id
    //id génerer automatiquement dans la base
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//pour valider les données on utilise 
// @NotBlank--> champ obligatoire
// @Email --> email vide 
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    //@Column(unique = true) email doit etre unique 
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Le prénom est obligatoire")
    //@size taille minimale (valider les données)
    @Size(min = 2, message = "Le prénom doit contenir au moins 2 caractères")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, message = "Le nom doit contenir au moins 2 caractères")
    private String lastName;
}
