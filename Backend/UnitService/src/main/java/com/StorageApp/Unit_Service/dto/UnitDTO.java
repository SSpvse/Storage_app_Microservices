package com.StorageApp.Unit_Service.dto;

import com.StorageApp.Unit_Service.model.Unit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitDTO {

    private Long id;
    private String name;
    private String description;
    private String location;

    public Unit DTO_to_Unit(){
        Unit unit = new Unit();
        unit.setId(id);
        unit.setName(name);
        unit.setDescription(description);
        unit.setLocation(location);
        return unit;
    }


}
