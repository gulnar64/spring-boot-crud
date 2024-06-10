package aze.coders.springbootcrud.config;

import aze.coders.springbootcrud.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
//import java.util.stream.Collectors;
//
//import static aze.coders.springbootcrud.service.impl.AuthServiceImpl.ACCESS_TOKEN;
//import static java.util.stream.Collectors.*;

@Configuration
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    public static final String AUTHORITIES = "authorities";
    public static final String BEARER = "Bearer ";
    public final static String ACCESS_TOKEN = "access_token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<Authentication> authenticationToken = authenticate(request);
        authenticationToken.ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authenticationToken.get()));
        filterChain.doFilter(request, response);
    }

    private Optional<Authentication> authenticate(HttpServletRequest request) {
//        Optional<String> token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION));
//        if (token.isPresent()) {
        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]);
        Optional<Cookie> cookie = Arrays.stream(cookies).filter(c -> c.getName().equals(ACCESS_TOKEN)).findFirst();
        if (cookie.isPresent()) {
            Claims claims = jwtService.parseToken(cookie.get().getValue());
            return Optional.of(new UsernamePasswordAuthenticationToken(claims.getSubject(), "", getAuthorities(claims)));
        }
        return Optional.empty();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
        List<?> roles = (List<?>) claims.get(AUTHORITIES);
        return roles.stream().map(auth -> new SimpleGrantedAuthority(auth.toString())).toList();
    }
}

