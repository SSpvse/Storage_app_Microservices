package com.StorageApp.Item_Service.Model;

import com.StorageApp.Item_Service.Model.DTO.DateDTO;
import com.StorageApp.Item_Service.Model.DTO.ItemDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    private String location;

    @Column(nullable = false)
    private Long quantity;

    private LocalDate date;

    @Column(nullable = false)
    private Long unitID;

    @Column(nullable = false)
    private Long userID;

    public DateDTO to_DateDTO() {
        DateDTO dateDTO = new DateDTO();
        dateDTO.setId(id);
        dateDTO.setDateTime(date);
        dateDTO.setUser_id(userID);
        return dateDTO;
    }
    public ItemDTO to_ItemDTO() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(id);
        itemDTO.setName(name);
        itemDTO.setDescription(description);
        itemDTO.setQuantity(quantity);
        itemDTO.setDate(date);
        itemDTO.setUnit_id(unitID);
        return itemDTO;
    }

}
