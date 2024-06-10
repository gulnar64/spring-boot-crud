package aze.coders.springbootcrud.service.impl;

import aze.coders.springbootcrud.entity.RefreshToken;
import aze.coders.springbootcrud.enums.ErrorDetails;
import aze.coders.springbootcrud.exception.UnAuthorizedException;
import aze.coders.springbootcrud.model.AccessTokenDto;
import aze.coders.springbootcrud.model.RefreshTokenDto;
import aze.coders.springbootcrud.model.SignInRequest;
import aze.coders.springbootcrud.model.SignInResponse;
import aze.coders.springbootcrud.repository.RefreshTokenRepository;
import aze.coders.springbootcrud.service.AuthService;
import aze.coders.springbootcrud.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.access-expire-time}")
    private long accessExpireTime;

    @Value("${jwt.refresh-expire-time}")
    private long refreshExpireTime;

    public final static String ACCESS_TOKEN = "access_token";
    public final static String REFRESH_TOKEN = "refresh_token";

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    private final ObjectMapper objectMapper;

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        AccessTokenDto accessTokenDto = new AccessTokenDto(jwtService.issueToken(authenticate));
        RefreshTokenDto refreshTokenDto = issueRefreshToken(signInRequest.getUsername());
        return new SignInResponse(accessTokenDto, refreshTokenDto);
    }

    private RefreshTokenDto issueRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), username, true, new Date(), new Date(new Date().getTime() + refreshExpireTime));
        refreshTokenRepository.save(refreshToken);
        return objectMapper.convertValue(refreshToken, RefreshTokenDto.class);
    }

    @Override
    public void setCookie(HttpHeaders httpHeaders, SignInResponse signInResponse) {
        ResponseCookie accessToken = ResponseCookie.from(ACCESS_TOKEN, signInResponse.getAccessTokenDto().getToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(accessExpireTime)
                .sameSite("LAX")
                .build();
        httpHeaders.add(HttpHeaders.SET_COOKIE, accessToken.toString());
        ResponseCookie refreshToken = ResponseCookie.from(REFRESH_TOKEN, signInResponse.getRefreshTokenDto().getToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(refreshExpireTime)
                .sameSite("LAX")
                .build();
        httpHeaders.add(HttpHeaders.SET_COOKIE, refreshToken.toString());
    }

    @Override
    public void clearCookie(HttpHeaders httpHeaders) {
        ResponseCookie accessToken = ResponseCookie.from(ACCESS_TOKEN, "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("LAX")
                .build();
        httpHeaders.add(HttpHeaders.SET_COOKIE, accessToken.toString());
        ResponseCookie refreshToken = ResponseCookie.from(REFRESH_TOKEN, "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("LAX")
                .build();
        httpHeaders.add(HttpHeaders.SET_COOKIE, refreshToken.toString());
    }

    @Override
    public void signOut(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken).ifPresent(
                refresh -> {
                    refresh.setValid(false);
                    refreshTokenRepository.save(refresh);
                }
        );
    }

    @Override
    public SignInResponse refreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new UnAuthorizedException(ErrorDetails.REFRESH_TOKEN_NOT_FOUND));
        if (!refreshToken.getValid())
            throw new UnAuthorizedException(ErrorDetails.REFRESH_TOKEN_IS_NOT_VALID);
        if (refreshToken.getExpireDate().before(new Date()))
            throw new UnAuthorizedException(ErrorDetails.REFRESH_TOKEN_IS_EXPIRED);

        UserDetails userDetails = userDetailsService.loadUserByUsername(refreshToken.getUsername());
        //if(!(userDetails.isAccountNonExpired() && userDetails.isEnabled() && userDetails.isAccountNonLocked() && userDetails.isCredentialsNonExpired()))
        if (!userDetails.isAccountNonExpired() || !userDetails.isEnabled() || !userDetails.isAccountNonLocked() || !userDetails.isCredentialsNonExpired())
            throw new UnAuthorizedException(ErrorDetails.USER_DETAIL_NOT_VALID);

        refreshToken.setValid(false);
        refreshTokenRepository.save(refreshToken);
        RefreshTokenDto refreshTokenDto = issueRefreshToken(refreshToken.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
        AccessTokenDto accessTokenDto = new AccessTokenDto(jwtService.issueToken(authentication));
        return new SignInResponse(accessTokenDto, refreshTokenDto);
    }
}
