package aze.coders.springbootcrud;

import aze.coders.springbootcrud.entity.Authority;
import aze.coders.springbootcrud.entity.User;
import aze.coders.springbootcrud.repository.AuthorityRepository;
import aze.coders.springbootcrud.repository.UserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Customer Api",
        description = "This is customer crud services",
        summary = "This is summary",
        version = "v1"
))
@RequiredArgsConstructor
public class SpringBootCrudApplication implements CommandLineRunner {
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCrudApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        Authority authority = new Authority();
//        authority.setAuthority("ROLE_USER");
//        authorityRepository.save(authority);
//
//        Authority authority2 = new Authority();
//        authority2.setAuthority("ROLE_ADMIN");
//        authorityRepository.save(authority2);
//
//        User user = new User();
//        user.setUsername("user");
//        user.setPassword(passwordEncoder.encode("12345"));
//        user.setEnabled(true);
//        user.setCredentialsNonExpired(true);
//        user.setAccountNonLocked(true);
//        user.setAccountNonExpired(true);
//        user.setAuthorities(List.of(authority));
//        userRepository.save(user);
//
//        User user2 = new User();
//        user2.setUsername("admin");
//        user2.setPassword(passwordEncoder.encode("12345"));
//        user2.setEnabled(true);
//        user2.setCredentialsNonExpired(true);
//        user2.setAccountNonLocked(true);
//        user2.setAccountNonExpired(true);
//        user2.setAuthorities(List.of(authority, authority2));
//        userRepository.save(user2);
    }
}
