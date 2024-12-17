package com.StorageApp.LoginService.controller;

import com.StorageApp.LoginService.model.User;
import com.StorageApp.LoginService.model.dto.LoginDTO;
import com.StorageApp.LoginService.model.dto.RegisterDTO;
import com.StorageApp.LoginService.model.dto.UserDTO;
import com.StorageApp.LoginService.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {


    LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    // LOGGING IN USER
    @GetMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginDTO) {

        UserDTO user = loginService.login(loginDTO);
        return ResponseEntity.ok(user);
    }

    // REGISTERING USER
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterDTO userDTO) {
        UserDTO respUserDTO = loginService.register(userDTO);
        return ResponseEntity.ok(respUserDTO);
    }

    // GETTING ID BY EMAIL
    @GetMapping("/email")
    public ResponseEntity<Long> getEmailById(@RequestBody String mail) {
        Long id = loginService.getIdByEmail(mail);
        return ResponseEntity.ok(id);
    }


}
