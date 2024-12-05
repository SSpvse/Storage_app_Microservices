package com.StorageManager.LoginService.repository;

import com.StorageManager.LoginService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<User, Integer> {


        Optional<User> findByEmail(String email);

  /*  @Repository
    public interface UserRepository extends CrudRepository<User, Integer> {
        Optional<User> findByEmail(String email);
    }


   */

}
