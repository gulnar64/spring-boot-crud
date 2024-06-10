package aze.coders.springbootcrud.service.impl;

import aze.coders.springbootcrud.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.access-expire-time}")
    private long expireTime;


    @Override
    public String issueToken(Authentication authentication) {
        return Jwts.builder().header()
                .add(Map.of("typ", "JWT", "alg", "HS256"))
                .and().claims()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .add("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .add("username", authentication.getName())
                .and().signWith(getSigningKey()).compact();
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
