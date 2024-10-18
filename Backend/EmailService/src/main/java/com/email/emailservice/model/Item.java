package com.email.emailservice.model;

import com.email.emailservice.model.DTO.DateDTO;
import com.email.emailservice.model.DTO.ItemDTO;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Item {

    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Long quantity;

    private String date;

    @Column(nullable = false)
    private Long unitID;

    @Column(nullable = false)
    private Long userID;

    public DateDTO to_DateDTO() {
        DateDTO dateDTO = new DateDTO();
        dateDTO.setId(id);
        dateDTO.setDate(date);
        return dateDTO;
    }
    public ItemDTO to_ItemDTO() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(id);
        itemDTO.setName(name);
        itemDTO.setDescription(description);
        itemDTO.setQuantity(quantity);
        itemDTO.setDate(date);
        itemDTO.setUnitID(unitID);
        itemDTO.setUserID(userID);
        return itemDTO;
    }

}
