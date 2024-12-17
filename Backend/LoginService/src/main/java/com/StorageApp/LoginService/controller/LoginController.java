package com.StorageApp.LoginService.controller;

import com.StorageApp.LoginService.model.dto.LoginDTO;
import com.StorageApp.LoginService.model.dto.RegisterDTO;
import com.StorageApp.LoginService.model.dto.UserDTO;
import com.StorageApp.LoginService.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {


    LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    // LOGGING IN USER
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginDTO) {

        try{
            UserDTO user = loginService.login(loginDTO);

            if (user == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // REGISTERING USER
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterDTO userDTO) {
        UserDTO respUserDTO = loginService.register(userDTO);
        return ResponseEntity.ok(respUserDTO);
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestBody String email) {
        // Log email for debugging
        System.out.println("Received email: " + email);
        System.out.println("HERE?? LOGINCONTROLLER");

        // Simulere sjekk i databasen
        boolean exists = email.equalsIgnoreCase("example@test.com");

        // Returner respons som JSON
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

}
