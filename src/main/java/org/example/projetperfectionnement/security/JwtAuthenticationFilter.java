package org.example.projetperfectionnement.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.projetperfectionnement.entity.Admin;
import org.example.projetperfectionnement.repository.AdminRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AdminRepository adminRepository;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, AdminRepository adminRepository) {
        this.jwtUtils = jwtUtils;
        this.adminRepository = adminRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // ⚡ Ignorer les routes register/login pour ne pas bloquer Postman
        if (request.getServletPath().startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Récupérer le header Authorization
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // Vérifier le token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            if (jwtUtils.validateJwtToken(token)) {
                email = jwtUtils.getEmailFromToken(token);
            }
        }

        // Si email présent et non authentifié, créer l'authentification Spring
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Admin admin = adminRepository.findByEmail(email).orElse(null);
            if (admin != null) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(admin, null, null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continuer la chaîne de filtres
        filterChain.doFilter(request, response);
    }
}
