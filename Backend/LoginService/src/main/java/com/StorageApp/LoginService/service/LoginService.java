package com.StorageApp.LoginService.service;

import com.StorageApp.LoginService.model.User;
import com.StorageApp.LoginService.model.dto.*;
import com.StorageApp.LoginService.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class LoginService {

    LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    // log in user with email and password
    public UserDTO login(LoginDTO loginDTO) {

        System.out.println("-X-X-X-X-- LOGIN SERVICE CALLED");
        System.out.println("-X-X-X-X-- the login DTO ::: " + loginDTO.toString());


        if (loginDTO == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        System.out.println("-X-X-X-X-- the email AFTER ASSIGNING FROM DTO TO STRINGS::: " + email);
        System.out.println("-X-X-X-X-- the password AFTER ASSIGNING FROM DTO TO STRINGS::: " + password);
        User user = loginRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }


    // register user
    public UserDTO register(RegisterDTO registerDTO) {

        System.out.println("-X-X-X-X-- REGISTER SERVICE CALLED");
        System.out.println("-X-X-X-X-- the register DTO ::: " + registerDTO.toString());
        if (registerDTO == null) {
            throw new IllegalArgumentException("User cannot be null");
        }else {
            String username = registerDTO.getUsername();
            String email = registerDTO.getEmail();
            String password = registerDTO.getPassword();
            // String role = registerDTO.getRole();
            // Role enumRole = Role.valueOf(role);

            System.out.println("LINE 57-X-X-X-X-- the username AFTER ASSIGNING FROM DTO TO STRINGS::: " + username);
            System.out.println("-X-X-X-X-- the email AFTER ASSIGNING FROM DTO TO STRINGS::: " + email);
            System.out.println("LINE 59-X-X-X-X-- the password AFTER ASSIGNING FROM DTO TO STRINGS::: " + password);

            User user = loginRepository.save(new User(username, email, password));

            System.out.println("LINE 63-x-x-x-x BEFORE RETURNING THE USER_DTO FROM REGISTER SERVICE" + user.toString());
            return new UserDTO( user.getId(), user.getUsername(),user.getEmail());
        }
    }

    // get id by email
    public Optional<UnitUserDTO> getUserByEmail(EmailDTO email) {
        // Get the user from the repository
        Optional<User> user = Optional.ofNullable(loginRepository.findByEmail(email.getEmail()));

        // If the user is not present, log a message and return an empty Optional
        if (user.isEmpty()) {
            System.out.println("User not found! (with email: " + email + ")");
            return Optional.empty();
        }

        // Map the user to the DTO and return it wrapped in an Optional
        UnitUserDTO userDTO = new UnitUserDTO(user.get().getId(), user.get().getUsername(), user.get().getEmail());
        return Optional.of(userDTO);
    }


    // get email by id
    public EmailDTO getEmailById(Long id) {
        EmailDTO emailDTO = new EmailDTO(loginRepository.findEmailById(id));
        return emailDTO;
    }

}
