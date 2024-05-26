package aze.coders.springbootcrud.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
    private final JwtFilterConfigurerAdapter jwtFilterConfigurerAdapter;

    //    @Bean
//    InMemoryUserDetailsManager userDetailsManager() {
//        return new InMemoryUserDetailsManager(
//                new User("user", "{noop}12345", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))),
//                new User("admin", "{noop}12345", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")))
//        );
//    }
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/public/**").permitAll()
                                .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());
        http.apply(jwtFilterConfigurerAdapter);
        return http.build();
    }

}
