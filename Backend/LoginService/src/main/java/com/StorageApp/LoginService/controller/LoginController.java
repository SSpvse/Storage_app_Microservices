package com.StorageApp.LoginService.controller;

import com.StorageApp.LoginService.model.User;
import com.StorageApp.LoginService.model.dto.*;
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

    // GETTING USER(UNITUSER) BY EMAIL
    @PostMapping("/email")
    public ResponseEntity<UnitUserDTO> getEmailById(@RequestBody EmailDTO mail) {
        System.out.println("MAIL BEING CALLED WITH EMAIL : :: : : ::: " + mail);
        UnitUserDTO user = loginService.getIdByEmail(mail);
        System.out.println("ID BEING RETURNED : :: : : ::: " + user);
        return ResponseEntity.ok(user);
    }


}
