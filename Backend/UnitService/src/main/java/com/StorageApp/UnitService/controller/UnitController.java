package com.StorageApp.UnitService.controller;

import com.StorageApp.UnitService.model.UnitUser;
import com.StorageApp.UnitService.model.UnitUserAccess;
import com.StorageApp.UnitService.model.dto.InviteGuestDTO;
import com.StorageApp.UnitService.model.dto.UnitDTO;
import com.StorageApp.UnitService.model.Unit;
import com.StorageApp.UnitService.repository.UnitUserRepository;
import com.StorageApp.UnitService.service.UnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        log.info("Reached controller");
        return ResponseEntity.ok(unitService.getAllUnits());
    }

    // Get a specific unit by id
    @GetMapping("/byid/{unitId}")
    public UnitDTO getUnitByID(@PathVariable Long unitId){

        try {
            Unit dto = unitService.getUnitById(unitId).DTO_to_Unit();
            return dto.Unit_to_DTO();
        } catch (Exception e){
            log.error("Error checking if unit exists", e);
            return null;
        }
    }

    // Check if unit exists ( this is for item service to call and get approved on unit exististing)
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> checkUnitExists(@PathVariable Long id) {

        try {
            boolean exists = unitService.unitExists(id);
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


    // update unit by userID
    @PostMapping("/update/{userID}")
    public ResponseEntity<Unit> updateItem(@RequestBody UnitDTO unit, @PathVariable Long userID) {

        Unit updatedUnit = unitService.updateUnit(unit, userID);
        return new ResponseEntity<>(updatedUnit, HttpStatus.OK);
    }


    // delete unit by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
        unitService.deleteUnit(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // invite guest to unit
    @PostMapping("/invite")
    public ResponseEntity<Long> inviteGuest(@RequestBody InviteGuestDTO invDTO) {
       Long unitUserAccess_id = unitService.inviteGuest(invDTO);
       return ResponseEntity.ok(unitUserAccess_id);
    }

    // get units when user is guest

    @GetMapping("/units-for-guest/{userId}")
    public ResponseEntity<List<UnitDTO>> getUnitsForGuest(@PathVariable Long userId) {
        // Assuming you have a method to retrieve UnitUser by userId
        List<Unit> unitList = unitService.getUnitsForGuestRole(userId);
        if (unitList == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        List<UnitDTO> dtoList = new ArrayList<>();
        for (Unit unit : unitList) {
            dtoList.add(unit.Unit_to_DTO());
        }
        return ResponseEntity.ok(dtoList);
    }

}
