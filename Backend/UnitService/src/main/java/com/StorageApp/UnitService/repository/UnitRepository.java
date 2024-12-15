package com.StorageApp.UnitService.repository;


import com.StorageApp.UnitService.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    Unit findUnitById(Long id);

    void deleteById(Long id);


}
