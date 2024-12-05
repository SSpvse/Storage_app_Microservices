package com.StorageManager.LoginService.service;

import com.StorageManager.LoginService.model.User;
import com.StorageManager.LoginService.repository.LoginRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final LoginRepository userRepository;

    public UserService(LoginRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        users.addAll(userRepository.findAll());

        return users;
    }
}