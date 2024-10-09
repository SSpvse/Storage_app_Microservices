package com.StorageApp.Item_Service.Model;

import com.StorageApp.Item_Service.Model.DTO.DateDTO;
import com.StorageApp.Item_Service.Model.DTO.ItemDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Long Unit_ID;

    @Column(nullable = false)
    private Long user_id;

    public DateDTO to_DateDTO() {
        DateDTO dateDTO = new DateDTO();
        dateDTO.setId(id);
        dateDTO.setDateTime(date);
        dateDTO.setUser_id(user_id);
        return dateDTO;
    }
    public ItemDTO to_ItemDTO() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(id);
        itemDTO.setName(name);
        itemDTO.setDescription(description);
        itemDTO.setQuantity(quantity);
        itemDTO.setDate(date);
        itemDTO.setUnit_id(Unit_ID);
        return itemDTO;
    }

}
