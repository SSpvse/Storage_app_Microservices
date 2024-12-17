package com.StorageApp.LoginService.controller;

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
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginDTO) {
        System.out.println("-x-x-x-x-x-x-x LOGIN CONTROLLER CALLED");
        System.out.println("-x-x-x-x-x-x-x the login DTO ::: " + loginDTO.toString());

        UserDTO user = loginService.login(loginDTO);
        return ResponseEntity.ok(user);
    }

    // REGISTERING USER
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterDTO userDTO) {

        // check if user already exists
        ResponseEntity<UnitUserDTO> unitexists = getUserByEmail(new EmailDTO(userDTO.getEmail()));

        if (unitexists.getBody() != null) {
            System.out.println("USER ALREADY EXISTS");
            return ResponseEntity.badRequest().build();
        }
        UserDTO respUserDTO = loginService.register(userDTO);
        return ResponseEntity.ok(respUserDTO);
    }

    // GETTING USER(UNITUSER) BY EMAIL
    @PostMapping("/email")
    public ResponseEntity<UnitUserDTO> getUserByEmail(@RequestBody EmailDTO mail) {
        System.out.println("MAIL BEING CALLED WITH EMAIL : :: : : ::: " + mail);
        UnitUserDTO user = loginService.getIdByEmail(mail);
        System.out.println("-X-X USER BEING RETURNED fromo  getUserByEmail method : :: : : ::: " + user.toString());
        return ResponseEntity.ok(user);
    }


}
