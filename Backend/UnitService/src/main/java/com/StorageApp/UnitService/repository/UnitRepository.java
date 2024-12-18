package com.StorageApp.UnitService.repository;

import com.StorageApp.UnitService.model.Unit;
import com.StorageApp.UnitService.model.UnitUserAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository for Unit
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Unit findUnitById(Long id);  // Custom query method
    void deleteById(Long id);   // Custom deletion method

    List<Unit> findByOwnerId(Long ownerId);
}

