package com.StorageApp.LoginService.controller;

import com.StorageApp.LoginService.model.dto.LoginDTO;
import com.StorageApp.LoginService.model.dto.RegisterDTO;
import com.StorageApp.LoginService.model.dto.UserDTO;
import com.StorageApp.LoginService.service.LoginServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/test")
public class LoginControllerTest {

    private final LoginServiceTest loginServiceTest;

    @Autowired
    public LoginControllerTest(LoginServiceTest loginServiceTest) {
        this.loginServiceTest = loginServiceTest;
    }

    // Log in user with email and password
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            UserDTO user = loginServiceTest.login(loginDTO);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    // Register user
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterDTO registerDTO) {
        try {
            UserDTO user = loginServiceTest.register(registerDTO);
            return ResponseEntity.status(201).body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }
    /*
    // Ny GET-rute for å sjekke om e-posten allerede finnes
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        System.out.println("Recived Email in backend" + email);
        boolean emailExists = loginServiceTest.checkIfEmailExists(email);
        return ResponseEntity.ok(emailExists);
    }*/

    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
        // Log email for debugging
        System.out.println("Received email: " + email);

        // Simulere sjekk i databasen
        boolean exists = email.equalsIgnoreCase("example@test.com");

        // Returner respons som JSON
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

}
