package org.example.projetperfectionnement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.projetperfectionnement.dto.LoginRequest;
import org.example.projetperfectionnement.dto.LoginResponse;
import org.example.projetperfectionnement.dto.RegisterRequest;
import org.example.projetperfectionnement.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        String message = authService.register(registerRequest);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}

