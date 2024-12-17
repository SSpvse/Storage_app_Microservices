package com.StorageApp.LoginService.service;

import com.StorageApp.LoginService.model.Role;
import com.StorageApp.LoginService.model.User;
import com.StorageApp.LoginService.model.dto.LoginDTO;
import com.StorageApp.LoginService.model.dto.RegisterDTO;
import com.StorageApp.LoginService.model.dto.UserDTO;
import com.StorageApp.LoginService.repository.LoginRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceTest {

    private final LoginRepositoryTest loginRepositoryTest;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginServiceTest(LoginRepositoryTest loginRepositoryTest, PasswordEncoder passwordEncoder) {
        this.loginRepositoryTest = loginRepositoryTest;
        this.passwordEncoder = passwordEncoder;
    }

    // log in user with email and password
    public UserDTO login(LoginDTO loginDTO) {
        if (loginDTO == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        // Finn bruker basert på e-post
        Optional<User> userOpt = loginRepositoryTest.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOpt.get();

        // Sammenlign passordet
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return new UserDTO(user.getId(), user.getUsername(), user.getRole());
    }

    // register user
    public UserDTO register(RegisterDTO registerDTO) {
        if (registerDTO == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        String username = registerDTO.getUsername();
        String email = registerDTO.getEmail();
        String password = registerDTO.getPassword();
        String role = registerDTO.getRole();

        // Sjekk om bruker eksisterer
        Optional<User> existingUser = loginRepositoryTest.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        // Håndter feil i role-parameteren
        Role enumRole;
        try {
            enumRole = Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        // Krypter passord før lagring
        String encryptedPassword = passwordEncoder.encode(password);

        // Lagre bruker
        User user = new User(username, email, encryptedPassword, enumRole);
        user = loginRepositoryTest.save(user);

        return new UserDTO(user.getId(), user.getUsername(), user.getRole());
    }

    // Checking if email exists
    public boolean checkIfEmailExists(String email) {
        return loginRepositoryTest.existsByEmail(email);
    }
}
