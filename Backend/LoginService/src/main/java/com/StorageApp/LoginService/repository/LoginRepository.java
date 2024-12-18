package com.StorageApp.LoginService.repository;

import com.StorageApp.LoginService.model.User;
import com.StorageApp.LoginService.model.dto.EmailDTO;
import com.StorageApp.LoginService.model.dto.RegisterDTO;
import com.StorageApp.LoginService.model.dto.UnitUserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    User findByEmailAndPassword(String email, String password);
    User save(User user);

    User findByEmail(String email);
}

