package aze.coders.springbootcrud.service;

import aze.coders.springbootcrud.model.SignInRequest;
import aze.coders.springbootcrud.model.SignInResponse;
import org.springframework.http.HttpHeaders;

public interface AuthService {
    SignInResponse signIn(SignInRequest signInRequest);

    void setCookie(HttpHeaders httpHeaders, SignInResponse signInResponse);

    void clearCookie(HttpHeaders httpHeaders);

    void signOut(String refreshToken);

    SignInResponse refreshToken(String refreshToken);
}
