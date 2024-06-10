package aze.coders.springbootcrud.controller;

import aze.coders.springbootcrud.model.SignInRequest;
import aze.coders.springbootcrud.model.SignInResponse;
import aze.coders.springbootcrud.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
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
