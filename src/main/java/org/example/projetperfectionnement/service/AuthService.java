package org.example.projetperfectionnement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projetperfectionnement.dto.LoginRequest;
import org.example.projetperfectionnement.dto.LoginResponse;
import org.example.projetperfectionnement.dto.RegisterRequest;
import org.example.projetperfectionnement.entity.Admin;
import org.example.projetperfectionnement.exception.BusinessException;
import org.example.projetperfectionnement.repository.AdminRepository;
import org.example.projetperfectionnement.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public String register(RegisterRequest registerRequest) {
        log.info("Tentative d'enregistrement pour l'email: {}", registerRequest.getEmail());
        
        if (adminRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new BusinessException("Un compte avec cet email existe déjà");
        }
        
        Admin admin = new Admin();
        admin.setEmail(registerRequest.getEmail());
        admin.setFirstName(registerRequest.getFirstName());
        admin.setLastName(registerRequest.getLastName());
        
        adminRepository.save(admin);
        
        log.info("Admin enregistré avec succès: {} {} {}", 
                registerRequest.getFirstName(), 
                registerRequest.getLastName(), 
                registerRequest.getEmail());
        return "Admin enregistré avec succès !";
    }

    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Tentative de connexion pour l'email: {}", loginRequest.getEmail());
        
        Admin admin = adminRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BusinessException("Email non trouvé"));
        
        String token = jwtUtils.generateToken(admin.getEmail());
        
        log.info("Connexion réussie pour l'email: {}", loginRequest.getEmail());
        
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setType("Bearer");
        response.setEmail(admin.getEmail());
        response.setFirstName(admin.getFirstName());
        response.setLastName(admin.getLastName());
        response.setMessage("Connexion réussie");
        
        return response;
    }
}
