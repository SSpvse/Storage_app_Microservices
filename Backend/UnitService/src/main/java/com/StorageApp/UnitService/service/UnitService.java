package com.StorageApp.UnitService.service;

import com.StorageApp.UnitService.dto.UnitDTO;
import com.StorageApp.UnitService.model.Unit;
import com.StorageApp.UnitService.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return unitRepository.findUnitByid(unitId).Unit_to_DTO();
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

    // Deleting a unit by the ID
    public void deleteUnit(Long unitId){
        unitRepository.deleteById(unitId);
    }
}
