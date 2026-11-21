package org.example.projetperfectionnement.exception;
//C’est une exception personnalisée utilisée quand une donnée n’existe pas (ex: admin ou interview introuvable)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}