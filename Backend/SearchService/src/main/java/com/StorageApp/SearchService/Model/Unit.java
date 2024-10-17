package com.StorageApp.SearchService.Model;

import com.StorageApp.SearchService.Model.DTO.UnitDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String location;

    public UnitDTO Unit_to_DTO(){
        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setId(id);
        unitDTO.setName(name);
        unitDTO.setDescription(description);
        unitDTO.setLocation(location);
        return unitDTO;
    }
}