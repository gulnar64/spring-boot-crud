package aze.coders.springbootcrud.controller;

import aze.coders.springbootcrud.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtController {
    private final JwtService jwtService;

    @GetMapping("/issue-token")
    public String issueToken(Authentication authentication) {
        return jwtService.issueToken(authentication);
    }

    @GetMapping("/parse-token")
    public Claims parseToken(@RequestParam String token) {
        return jwtService.parseToken(token);
    }
}
