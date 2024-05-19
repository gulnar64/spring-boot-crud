package aze.coders.springbootcrud.repository;

import aze.coders.springbootcrud.entity.Authority;
import aze.coders.springbootcrud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
