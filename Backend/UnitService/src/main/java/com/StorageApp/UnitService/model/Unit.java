package com.StorageApp.UnitService.model;

import com.StorageApp.UnitService.model.dto.UnitDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;
    private String location;
    private String type;

    @Column(nullable = false)
    private Boolean editPermission;

    @Column(nullable = false)
    private Long ownerId;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    private List<UnitUserAccess> userAccessList;


    public UnitDTO Unit_to_DTO(){
        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setId(id);
        unitDTO.setName(name);
        unitDTO.setDescription(description);
        unitDTO.setLocation(location);
        unitDTO.setType(type);
        unitDTO.setEditPermission(editPermission);
        unitDTO.setOwnerId(ownerId);
        return unitDTO;
    }
}
