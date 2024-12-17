package com.StorageApp.UnitService.service;

import com.StorageApp.UnitService.model.Role;
import com.StorageApp.UnitService.model.UnitUser;
import com.StorageApp.UnitService.model.UnitUserAccess;
import com.StorageApp.UnitService.model.dto.EmailDTO;
import com.StorageApp.UnitService.model.dto.InviteGuestDTO;
import com.StorageApp.UnitService.model.dto.UnitDTO;
import com.StorageApp.UnitService.model.Unit;
import com.StorageApp.UnitService.repository.UUAccessRepository;
import com.StorageApp.UnitService.repository.UnitRepository;
import com.StorageApp.UnitService.repository.UnitUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UnitService {


    private UnitRepository unitRepository;

    private UUAccessRepository uuAccessRepository;

    private UnitUserRepository unitUserRepository;

    @Autowired
    public UnitService(UnitRepository unitRepository, UUAccessRepository uuAccessRepository, UnitUserRepository unitUserRepository) {
        this.unitRepository = unitRepository;
        this.uuAccessRepository = uuAccessRepository;
        this.unitUserRepository = unitUserRepository;
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

        System.out.println("INSIDE UPDATE UNIT SERVICE");
        System.out.println("UNIT ID: " + unit.getId());
        System.out.println("UNIT NAME: " + unit.getName());
        System.out.println("UNIT DESCRIPTION: " + unit.getDescription());
        System.out.println("UNIT LOCATION: " + unit.getLocation());
        System.out.println("UNIT TYPE: " + unit.getType());
        System.out.println("UNIT owner ID: " + unit.getOwnerId());

        Unit existingUnit = unitRepository.findById(unit.getId())
                .orElseThrow(() -> new RuntimeException("Unit with ID " + unit.getId() + " not found"));

        System.out.println(" -x-x-x-x-x-x-x   EXISTING UNIT ID: " + existingUnit.getId() + " -- AND OWNER ID: " + existingUnit.getOwnerId() + "-x-x-x-x-x-x-x  ");

        if (Objects.equals(existingUnit.getOwnerId(), userID)) {
            System.out.println("OWNER ID IS SAME AS USER_ID: " + userID + " -- AND OWNER ID: " + existingUnit.getOwnerId());
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
            return existingUnit;
        }

        // IF OWNER ID IS NOT SAME AS USER ID of the unit then check if the user has access to the unit through the UnitUserAccess table

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
    public Long inviteGuest(InviteGuestDTO invDTO) {

        System.out.println("--- - --- INVITING GUEST DTO : " + invDTO.toString());
        String url = "http://loginservice:8080/auth/email";

        // Set the body (email) to send
        EmailDTO emailBody = new EmailDTO(invDTO.getGuestEmail());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap the body and headers into an HttpEntity
        HttpEntity<EmailDTO> requestEntity = new HttpEntity<>(emailBody, headers);


        UnitUser user = null;
        try {
            // Send POST request to the AuthService
            ResponseEntity<UnitUser> response = restTemplate.postForEntity(url, requestEntity, UnitUser.class);

            // Check if the response is ok
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                user = response.getBody();
                System.out.println("Received User ID: " + user.toString());
                unitUserRepository.save(user);
            } else {
                throw new RuntimeException("Failed to retrieve User ID. Response: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while calling AuthService: " + e.getMessage());
        }

        // check if the unit exists in the database
        Unit unit = unitRepository.findUnitById(invDTO.getUnitID());
        if (unit == null) {
            throw new RuntimeException("Unit not found with ID: " + invDTO.getUnitID());
        }

        // unit and user access table object, to keep track of permissions:
        // setting the pariring for : unit / user  / role (guest)or(owner)
        UnitUserAccess unitUserAccess = new UnitUserAccess();

        unitUserAccess.setUnit(unit);
        unitUserAccess.setUser(user);

        if (invDTO.getRole() == Role.GUEST) {
            unitUserAccess.setRole(Role.GUEST);
        } else if (invDTO.getRole() == Role.OWNER) {
            unitUserAccess.setRole(Role.OWNER);
        }
        uuAccessRepository.save(unitUserAccess);

        return unitUserAccess.getId();
    }


}
