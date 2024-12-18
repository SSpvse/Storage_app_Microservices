package com.StorageApp.LoginService.controller;

import com.StorageApp.LoginService.model.dto.*;
import com.StorageApp.LoginService.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        System.out.println("LINE 24-x-x-x-x-x-x-x LOGIN CONTROLLER CALLED");
        System.out.println("-x-x-x-x-x-x-x the login DTO ::: " + loginDTO.toString());

        UserDTO user = loginService.login(loginDTO);
        return ResponseEntity.ok(user);
    }

    // REGISTERING USER
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterDTO userDTO) {

        // Get the Optional containing the UnitUserDTO, not just the value
        Optional<UnitUserDTO> unitUserDTOOptional = loginService.getUserByEmail(new EmailDTO(userDTO.getEmail()));

        // Check if the Optional contains a value
        if (unitUserDTOOptional.isPresent()) {
            UnitUserDTO unitUserDTO = unitUserDTOOptional.get(); // unwrap the value from the Optional
            System.out.println("LINE 38, LOGINCONTROLLER -X-X-X-X-X- unitexists.getBody()" + unitUserDTO.toString());
            System.out.println("USER ALREADY EXISTS");
            return ResponseEntity.badRequest().build();
        }

        // Proceed with registration if user doesn't exist
        UserDTO respUserDTO = loginService.register(userDTO);

        System.out.println("LINE 45, LOGINSERVICE-x-x-x-x-x-x-x the userDTO ::: " + respUserDTO.toString());
        return ResponseEntity.ok(respUserDTO);
    }


    // GETTING USER(UNITUSER) BY EMAIL
    @PostMapping("/email")
    public ResponseEntity<UnitUserDTO> getUserByEmail(@RequestBody EmailDTO mail) {
        System.out.println("MAIL BEING CALLED WITH EMAIL : :: : : ::: " + mail);
        Optional<UnitUserDTO> user = loginService.getUserByEmail(mail);

        System.out.println("line 55X_X_X_X_-X-X USER BEING RETURNED from  getUserByEmail method : :: : : ::: " + user.toString());
        if (user.isEmpty()) {
            return null;
        }else {
            System.out.println("-X-X USER BEING RETURNED from  getUserByEmail method : :: : : ::: " + user.toString());
            return ResponseEntity.ok(user.get());
        }
    }


    @GetMapping("/getMail-by-id/{id}")
    public ResponseEntity<EmailDTO> getMailById(@PathVariable Long id) {
        EmailDTO emailDTO = loginService.getEmailById(id);
        System.out.println("LINE 67, LOGINCONTROLLER -X-X-X-X-X- getMailById.getBody()" + emailDTO.toString());
        return ResponseEntity.ok(emailDTO);
    }

}
