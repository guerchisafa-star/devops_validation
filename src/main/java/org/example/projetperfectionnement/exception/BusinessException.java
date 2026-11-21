package org.example.projetperfectionnement.exception;
//exception personalisé pour signaler des erreurs métier (ex: email déjà utilisé).
//C’est une exception non vérifiée (unchecked) en Java
//RuntimeException = erreur qui arrive pendant l’exécution et qu’on n’est pas obligé de gérer explicitement.
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}