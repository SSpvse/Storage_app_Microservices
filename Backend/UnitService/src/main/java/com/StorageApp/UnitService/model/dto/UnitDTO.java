package com.StorageApp.UnitService.model.dto;

import com.StorageApp.UnitService.model.Unit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitDTO {

    private Long id;
    private String name;
    private String description;
    private String location;
    private String type;

    public Unit DTO_to_Unit(){
        Unit unit = new Unit();
        unit.setId(id);
        unit.setName(name);
        unit.setDescription(description);
        unit.setLocation(location);
        unit.setType(type);
        return unit;
    }


}
