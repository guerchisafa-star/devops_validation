package org.example.projetperfectionnement.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
//ErrorResponse permet de renvoyer des erreurs claires et organisées au frontend.
@Data
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> details;
}