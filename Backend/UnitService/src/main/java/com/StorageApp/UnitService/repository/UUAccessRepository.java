package com.StorageApp.UnitService.repository;

import com.StorageApp.UnitService.model.Role;
import com.StorageApp.UnitService.model.UnitUserAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// Repository for UnitUserAccess
public interface UUAccessRepository extends JpaRepository<UnitUserAccess, Long> {

    @Query("SELECT uua FROM UnitUserAccess uua WHERE uua.unit.id = :unitId AND uua.user.id = :userId")
    Optional<UnitUserAccess> findAccessByUnitAndUser(@Param("unitId") Long unitId, @Param("userId") Long userId);


    List<UnitUserAccess> findByUserIdAndRole(Long userId, Role role);

}

