package com.StorageApp.UnitService.service;

import com.StorageApp.UnitService.model.Role;
import com.StorageApp.UnitService.model.UnitUser;
import com.StorageApp.UnitService.model.UnitUserAccess;
import com.StorageApp.UnitService.model.dto.InviteGuestDTO;
import com.StorageApp.UnitService.model.dto.UnitDTO;
import com.StorageApp.UnitService.model.Unit;
import com.StorageApp.UnitService.repository.UUAccessRepository;
import com.StorageApp.UnitService.repository.UnitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class UnitService {


    private UnitRepository unitRepository;

    private UUAccessRepository uuAccessRepository;


    @Autowired
    public UnitService(UnitRepository unitRepository, UUAccessRepository uuAccessRepository) {
        this.unitRepository = unitRepository;
        this.uuAccessRepository = uuAccessRepository;
    }

    private final RestTemplate restTemplate = new RestTemplate();

    // Creating/Adding a new Unit
    public UnitDTO createUnit(UnitDTO unitDTO) {
        // Converting the DTO to Entity
        Unit unit = unitDTO.DTO_to_Unit();

        // Save the unit in the repository
        Unit savedunit = unitRepository.save(unit);
        if (savedunit == null) {
            throw new RuntimeException("Error saving unit");
        }else {
            // Return the updated DTO with the saved entity's details
            return savedunit.Unit_to_DTO();
        }
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


    // update unit
    @Transactional
    public Unit updateUnit(Unit unit, Long userID) {


        Unit existingUnit = unitRepository.findById(unit.getId())
                .orElseThrow(() -> new RuntimeException("Unit with ID " + unit.getId() + " not found"));

        UnitUserAccess uua = uuAccessRepository.findAccessByUnitAndUser(existingUnit.getId(), userID)
                .orElseThrow(() -> new RuntimeException("User with ID " + userID + " does not have access to unit with ID " + unit.getId()));

        if (uua.getRole() != Role.OWNER) {
            throw new RuntimeException("User with ID " + userID + " does not have permission to update unit with ID " + unit.getId());
        }else {
            if (unit.getDescription() != null && !unit.getDescription().isEmpty()) {
                existingUnit.setDescription(unit.getDescription());
            }
            if (unit.getLocation() != null && !unit.getLocation().isEmpty()) {
                existingUnit.setLocation(unit.getLocation());
            }
            if (unit.getName() != null && !unit.getName().isEmpty()) {
                existingUnit.setName(unit.getName());
            }
            if (unit.getType() != null && !unit.getType().isEmpty()) {
                existingUnit.setType(unit.getType());
            }
        }

        return unitRepository.save(existingUnit);
    }

    //delete unit
    @Transactional
    public void deleteUnit(Long id) {
        Optional<Unit> item = unitRepository.findById(id);
        if (item.isEmpty()) {
            throw new RuntimeException("Item not found with ID: " + id);
        }else {
            unitRepository.deleteById(id);
        }
    }

    // invite guest to unit
    public UnitUserAccess inviteGuest(InviteGuestDTO invDTO) {

        System.out.println("--- - --- INVITING GUEST DTO : " + invDTO.toString());
        // Create the URL for the invitation
        String url = String.format("http://gateway:8000/auth/invite/%d", invDTO.getUnitID());

        ResponseEntity<Long> userId = restTemplate.getForEntity(url, Long.class);
        if (userId == null) {
            throw new RuntimeException("User not found with ID: " + invDTO.getUnitID());
        }

        Unit unit = unitRepository.findUnitById(invDTO.getUnitID());
        if (unit == null) {
            throw new RuntimeException("Unit not found with ID: " + invDTO.getUnitID());
        }

        // unit and user access table object, to keep track of permissions:
        UnitUserAccess unitUserAccess = new UnitUserAccess();

        unitUserAccess.setUnit(unit);
        unitUserAccess.setUser(new UnitUser(userId.getBody()));

        if (invDTO.getRole() == Role.GUEST){
            unitUserAccess.setRole(Role.GUEST);
        }else if (invDTO.getRole() == Role.OWNER){
            unitUserAccess.setRole(Role.OWNER);
        }
        return uuAccessRepository.save(unitUserAccess);
    }


}
