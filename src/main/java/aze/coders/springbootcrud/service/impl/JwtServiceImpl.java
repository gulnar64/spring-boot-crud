package aze.coders.springbootcrud.service.impl;

import aze.coders.springbootcrud.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Override
    public String issueToken(Authentication authentication) {
        return Jwts.builder().header().add(Map.of("typ", "JWT", "alg", "HS256")).and().claims().subject("user").issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + 1000 * 60 + 24)).add("roles", List.of("ROLES_ADMIN", "ROLES_USER")).add("username", "user").and().signWith(getSigningKey()).compact();
    }

    @Override
    public Claims parseToken(String token) {
        return (Claims) Jwts.parser().verifyWith(getSigningKey()).build().parse(token).getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
