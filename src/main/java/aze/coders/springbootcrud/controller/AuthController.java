package aze.coders.springbootcrud.controller;

import aze.coders.springbootcrud.model.SignInRequest;
import aze.coders.springbootcrud.model.SignInResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${jwt.expire-time}")
    private long expireTime;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public final static String TOKEN = "token";

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        HttpHeaders httpHeaders = new HttpHeaders();
        SignInResponse signInResponse = new SignInResponse(jwtService.issueToken(authenticate));
        setCookie(httpHeaders, signInResponse);
        return ResponseEntity.ok(signInResponse);
    }

    public void setCookie(HttpHeaders httpHeaders, SignInResponse signInResponse) {
        ResponseCookie token = ResponseCookie.from(TOKEN, signInResponse.getToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(expireTime)
                .sameSite("LAX")
                .build();
        httpHeaders.add(HttpHeaders.SET_COOKIE, token.toString());
    }
}
