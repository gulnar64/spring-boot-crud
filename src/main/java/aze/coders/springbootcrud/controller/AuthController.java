package aze.coders.springbootcrud.controller;

import aze.coders.springbootcrud.model.AccessTokenDto;
import aze.coders.springbootcrud.model.RefreshTokenDto;
import aze.coders.springbootcrud.model.SignInRequest;
import aze.coders.springbootcrud.model.SignInResponse;
import aze.coders.springbootcrud.service.AuthService;
import aze.coders.springbootcrud.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static aze.coders.springbootcrud.service.impl.AuthServiceImpl.REFRESH_TOKEN;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Value("${jwt.access-expire-time}")
    private long accessExpireTime;

    @Value("${jwt.refresh-expire-time}")
    private long refreshExpireTime;

    public final static String ACCESS_TOKEN = "access_token";
    public final static String REFRESH_TOKEN = "refresh_token";

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(
            @RequestBody SignInRequest signInRequest) {
        SignInResponse signInResponse = authService.signIn(signInRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        authService.setCookie(httpHeaders, signInResponse);
        return new ResponseEntity<>(signInResponse, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> signIn(@CookieValue(value = REFRESH_TOKEN, defaultValue = "") String refreshToken) {
        authService.signOut(refreshToken);
        HttpHeaders httpHeaders = new HttpHeaders();
        authService.clearCookie(httpHeaders);
        return new ResponseEntity<>(httpHeaders, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<SignInResponse> refreshToken(@CookieValue(value = REFRESH_TOKEN, defaultValue = "") String refreshToken) {
        SignInResponse signInResponse = authService.refreshToken(refreshToken);
        HttpHeaders httpHeaders = new HttpHeaders();
        authService.setCookie(httpHeaders, signInResponse);
        return new ResponseEntity<>(signInResponse, httpHeaders, HttpStatus.OK);
    }

}
