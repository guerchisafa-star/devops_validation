package org.example.projetperfectionnement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, message = "Le prénom doit contenir au moins 2 caractères")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, message = "Le nom doit contenir au moins 2 caractères")
    private String lastName;
}