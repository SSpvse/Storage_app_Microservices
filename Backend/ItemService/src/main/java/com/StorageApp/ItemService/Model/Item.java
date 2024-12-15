package com.StorageApp.ItemService.Model;

import com.StorageApp.ItemService.Model.DTO.DateDTO;
import com.StorageApp.ItemService.Model.DTO.ItemDTO;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;


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
        dateDTO.setDate(date);
        dateDTO.setUserID(userID);
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

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", date=" + date +
                ", unitID=" + unitID +
                ", userID=" + userID +
                '}';
    }
}
