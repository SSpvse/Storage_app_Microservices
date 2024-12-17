package com.StorageApp.UnitService.repository;

import com.StorageApp.UnitService.model.UnitUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitUserRepository extends JpaRepository<UnitUser, Long> {
    UnitUser findByUsername(String username);
    UnitUser save(UnitUser user);
    UnitUser findByEmail(String email);




}
