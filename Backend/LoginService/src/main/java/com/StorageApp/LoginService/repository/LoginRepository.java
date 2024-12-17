package com.StorageApp.LoginService.repository;

import com.StorageApp.LoginService.model.User;
import com.StorageApp.LoginService.model.dto.RegisterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    User findByEmailAndPassword(String email, String password);
    User save(User user);
}
