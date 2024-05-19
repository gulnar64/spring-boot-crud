package aze.coders.springbootcrud.repository;

import aze.coders.springbootcrud.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
