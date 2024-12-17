package com.StorageApp.LoginService.repository;

import com.StorageApp.LoginService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepositoryTest extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    // Ny metode for å sjekke om en bruker med en spesifisert e-post finnes
    boolean existsByEmail(String email);

}