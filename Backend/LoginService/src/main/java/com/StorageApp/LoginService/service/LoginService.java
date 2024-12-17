package com.StorageApp.LoginService.service;

import com.StorageApp.LoginService.model.Role;
import com.StorageApp.LoginService.model.User;
import com.StorageApp.LoginService.model.dto.LoginDTO;
import com.StorageApp.LoginService.model.dto.RegisterDTO;
import com.StorageApp.LoginService.model.dto.UserDTO;
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

        if (loginDTO == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        User user = loginRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return new UserDTO(user.getId(), user.getUsername(), user.getRole());
    }


    // register user
    public UserDTO register(RegisterDTO registerDTO) {

        if (registerDTO == null) {
            throw new IllegalArgumentException("User cannot be null");
        }else {
            String username = registerDTO.getUsername();
            String email = registerDTO.getEmail();
            String password = registerDTO.getPassword();
            String role = registerDTO.getRole();
            Role enumRole = Role.valueOf(role);


            User user = loginRepository.save(new User(username, email, password, enumRole));
            return new UserDTO(user.getId(), user.getUsername(), user.getRole());
        }
    }

    // Checking if email exists
    public boolean checkIfEmailExists(String email) {
        return loginRepository.existsByEmail(email);
    }

}
