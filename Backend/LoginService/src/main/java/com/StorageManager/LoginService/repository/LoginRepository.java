package com.StorageManager.LoginService.repository;

import com.StorageManager.LoginService.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface LoginRepository {

    @Repository
    public interface UserRepository extends CrudRepository<User, Integer> {
        Optional<User> findByEmail(String email);
    }
}
