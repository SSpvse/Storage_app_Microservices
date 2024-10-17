package com.StorageApp.Unit_Service.controller;

import com.StorageApp.Unit_Service.dto.UnitDTO;
import com.StorageApp.Unit_Service.model.Unit;
import com.StorageApp.Unit_Service.service.UnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/unit")
public class UnitController {

    @Autowired
    private UnitService unitService;

    // Get all units
    @GetMapping("/getall")
    public ResponseEntity<List<Unit>> getAllUnits(){
        return ResponseEntity.ok(unitService.getAllUnits());
    }

    // Get a specific unit by id
    @GetMapping("/getunitbyid/{unitId}")
    public ResponseEntity<Boolean> checkUnitExists(@PathVariable Long unitId){

        try {
            boolean exists = unitService.unitExists(unitId);
            return ResponseEntity.ok(exists);
        } catch (Exception e){
            log.error("Error checking if unit exists", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    // Adding a unit
    @PostMapping("/addunit")
    public ResponseEntity<UnitDTO> createUnit(@RequestBody UnitDTO unitDTO){
        UnitDTO createdUnit = unitService.createUnit(unitDTO);
        return ResponseEntity.ok(createdUnit);
    }

    // Update an existing unit by id
    @PutMapping("/updateunit/{id}")
    public ResponseEntity<UnitDTO> updateUnit(@PathVariable("id") Long id, @RequestBody UnitDTO unitDTO) {
        // Call the service method to update the unit
        UnitDTO updatedUnitDTO = unitService.updateUnit(id, unitDTO);

        // Return the updated unit
        return new ResponseEntity<>(updatedUnitDTO, HttpStatus.OK);
    }

    // Delete a unit by ID
    @DeleteMapping("/deleteunit/{id}")
    public ResponseEntity<Void> deleteUnit(@PathVariable("id") Long id) {
        if (!unitService.unitExists(id)) {
            // Return 404 if unit doesn't exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        unitService.deleteUnit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
