package org.example.projetperfectionnement.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    private final SecretKey jwtSecret;
    private final long jwtExpirationMs;

    public JwtUtils(@Value("${jwt.secret}") String secret, 
                    @Value("${jwt.expiration}") long expiration) {
        this.jwtSecret = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationMs = expiration;
    }

    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtSecret, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException e) {
            log.error("Erreur lors de l'extraction de l'email du token: {}", e.getMessage());
            return null;
        }
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token JWT expiré: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Token JWT non supporté: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Token JWT malformé: {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("Signature JWT invalide: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Token JWT vide: {}", e.getMessage());
        }
        return false;
    }
}
