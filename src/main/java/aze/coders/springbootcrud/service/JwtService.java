package aze.coders.springbootcrud.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface JwtService {
    String issueToken(Authentication authentication);

    Claims parseToken(String token);
}
