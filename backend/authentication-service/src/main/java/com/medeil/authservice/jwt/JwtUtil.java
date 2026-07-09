package com.medeil.authservice.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    private static final String ACCESS = "ACCESS";
    private static final String REFRESH = "REFRESH";
    private static final String ISSUER = "authentication-service";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private SecretKey getKey() {
        return key;
    }

    // ===================================================
    // ACCESS TOKEN
    // ===================================================

    public String generateAccessToken(Long userId,
                                      String username,
                                      String role) {

        Date now = new Date();

        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .claim("roles", List.of(role))
                .claim("type", ACCESS)
                .issuer(ISSUER)
                .id(UUID.randomUUID().toString())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + accessExpiration))
                .signWith(getKey())
                .compact();
    }

    // ===================================================
    // REFRESH TOKEN
    // ===================================================

    public String generateRefreshToken(Long userId,
                                       String username) {

        Date now = new Date();

        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .claim("type", REFRESH)
                .issuer(ISSUER)
                .id(UUID.randomUUID().toString())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + refreshExpiration))
                .signWith(getKey())
                .compact();
    }

    // ===================================================
    // CLAIMS
    // ===================================================

    public Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

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

    public String extractJwtId(String token) {
        return extractClaims(token).getId();
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    // ===================================================
    // TOKEN CHECKS
    // ===================================================

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isAccessToken(String token) {
        return ACCESS.equals(extractTokenType(token));
    }

    public boolean isRefreshToken(String token) {
        return REFRESH.equals(extractTokenType(token));
    }

    // ===================================================
    // VALIDATION
    // ===================================================

    public boolean validateAccessToken(String token,
                                       String username) {

        try {

            Claims claims = extractClaims(token);

            return claims.getSubject().equals(username)
                    && ACCESS.equals(claims.get("type"))
                    && ISSUER.equals(claims.getIssuer())
                    && !claims.getExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException e) {

            return false;
        }
    }

    public boolean validateRefreshToken(String token,
                                        String username) {

        try {

            Claims claims = extractClaims(token);

            return claims.getSubject().equals(username)
                    && REFRESH.equals(claims.get("type"))
                    && ISSUER.equals(claims.getIssuer())
                    && !claims.getExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException e) {

            return false;
        }
    }
}