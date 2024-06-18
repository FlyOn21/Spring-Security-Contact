package org.example.app.springsecuritycontact.auth_app.repository;

import org.example.app.springsecuritycontact.auth_app.entitys.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories(basePackages = "org.example.app.springbootfxwebcontacts.auth.repository")
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
