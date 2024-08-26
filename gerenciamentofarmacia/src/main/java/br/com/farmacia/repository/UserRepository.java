package br.com.farmacia.repository;

import br.com.farmacia.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // List<User> findByRoleNot(Role role);
}