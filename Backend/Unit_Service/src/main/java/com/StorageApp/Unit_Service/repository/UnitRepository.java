package com.StorageApp.Unit_Service.repository;


import com.StorageApp.Unit_Service.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {
}
