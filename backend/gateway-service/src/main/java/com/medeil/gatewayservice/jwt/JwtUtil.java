package com.medeil.gatewayservice.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String REFRESH_TOKEN = "REFRESH";
    private static final String ISSUER = "authentication-service";

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // =====================================================
    // PARSE JWT
    // =====================================================

    public Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // =====================================================
    // EXTRACT VALUES
    // =====================================================

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public Long extractUserId(String token) {
        return extractClaims(token).get("userId", Long.class);
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return extractClaims(token).get("roles", List.class);
    }

    public String extractTokenType(String token) {
        return extractClaims(token).get("type", String.class);
    }

    public String extractIssuer(String token) {
        return extractClaims(token).getIssuer();
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    // =====================================================
    // TOKEN TYPE
    // =====================================================

    public boolean isAccessToken(String token) {
        return ACCESS_TOKEN.equals(extractTokenType(token));
    }

    public boolean isRefreshToken(String token) {
        return REFRESH_TOKEN.equals(extractTokenType(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // =====================================================
    // VALIDATION
    // =====================================================

    public boolean validateToken(String token) {

        try {

            Claims claims = extractClaims(token);

            return ISSUER.equals(claims.getIssuer())
                    && !claims.getExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException e) {

            return false;
        }
    }

}