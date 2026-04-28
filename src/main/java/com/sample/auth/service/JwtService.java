package com.sample.auth.service;

import com.sample.auth.module.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key;

    private static final long EXP_TIME = 1000 * 60 * 15; // note: ms*s*m

    public JwtService(@Value("${jwt.secret-key}")String secret) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateToken(@NonNull User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .claim("name", user.getFullName())
                .claim("picture", user.getProfilePictureUrl())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXP_TIME))
                .signWith(key)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserEmail(String token) {
        return String.valueOf(extractClaims(token).containsValue("email"));
    }

    public Boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public Boolean isTokenValid(String token, User user) {
        String email = extractUserEmail(token);
        return email.equals(user.getEmail()) && !isTokenExpired(token);
    }

}
