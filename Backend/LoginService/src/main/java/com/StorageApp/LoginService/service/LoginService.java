package com.StorageApp.LoginService.service;

import com.StorageApp.LoginService.model.User;
import com.StorageApp.LoginService.model.dto.*;
import com.StorageApp.LoginService.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
        return new UserDTO(user.getId(), user.getUsername());
    }


    // register user
    public UserDTO register(RegisterDTO registerDTO) {

        if (registerDTO == null) {
            throw new IllegalArgumentException("User cannot be null");
        }else {
            String username = registerDTO.getUsername();
            String email = registerDTO.getEmail();
            String password = registerDTO.getPassword();
            // String role = registerDTO.getRole();
            // Role enumRole = Role.valueOf(role);


            User user = loginRepository.save(new User(username, email, password));
            return new UserDTO( user.getId(), user.getUsername(),user.getEmail());
        }
    }

    // get id by email
    public UnitUserDTO getIdByEmail(EmailDTO email) {


        User user = loginRepository.findByEmail(email.getEmail());

        UnitUserDTO userDTO = new UnitUserDTO(user.getId(), user.getUsername(), user.getEmail());
        if (user == null) {
            throw new IllegalArgumentException("User not found! ( with email: " + email +")");
        }
        return userDTO;
    }
}
