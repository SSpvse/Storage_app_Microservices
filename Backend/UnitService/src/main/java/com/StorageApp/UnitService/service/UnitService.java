package com.StorageApp.UnitService.service;

import com.StorageApp.UnitService.dto.UnitDTO;
import com.StorageApp.UnitService.model.Unit;
import com.StorageApp.UnitService.repository.UnitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    // Creating/Adding a new Unit
    public UnitDTO createUnit(UnitDTO unitDTO) {

        // Converting the DTO to Entity
        Unit unit = unitDTO.DTO_to_Unit();

        // Save the unit in the repository
        unit = unitRepository.save(unit);

        // Return the updated DTO with the saved entity's details
        return unit.Unit_to_DTO();
    }

    // Get all units
    public List<Unit> getAllUnits(){
        return unitRepository.findAll();
    }

    // Checking if a unit exists by ID
    public boolean unitExists(Long unitId){
        // Return true if the Unit exists
        return unitRepository.existsById(unitId);
    }

    public UnitDTO getUnitById(Long unitId){
        return unitRepository.findUnitById(unitId).Unit_to_DTO();
    }


    /*
    // Updating a unit
    public UnitDTO updateUnit(Long unitId, UnitDTO unitDTO) {
        // Fetch the existing unit from the repository
        unitRepository.findById(unitId)
                .orElseThrow(() -> new Error("Unit not found with ID: " + unitId));

        Unit newUnit = unitDTO.DTO_to_Unit();
        //Unit updatedUnit = unitRepository.updateUnitByid(unitId, newUnit);

        //Convert the updated unit back to DTO and return
        return updatedUnit.Unit_to_DTO();
    }

     */

    // update unit
    @Transactional
    public Unit updateUnit(Unit unit, Long id) {
        Optional<Unit> oldUnit = unitRepository.findById(id);
        if (oldUnit.isPresent()) {
            unitRepository.deleteById(id);

            return unitRepository.save(unit);
        }else {
            return null;
        }
    }

    @Transactional
    public void deleteUnit(Long id) {
        Optional<Unit> item = unitRepository.findById(id);
        if (item.isEmpty()) {
            throw new RuntimeException("Item not found with ID: " + id);
        }else {
            unitRepository.deleteById(id);
        }
    }


}
