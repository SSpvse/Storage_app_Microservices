package com.StorageApp.ItemService.Model.DTO;

import com.StorageApp.ItemService.Model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDTO {

    private String name;
    private String description;
    private Long quantity;
    private LocalDate date;
    private Long unitID;
    private Long userID;

    @Override
    public String toString() {
        return "ItemDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", date=" + date +
                ", unitID=" + unitID +
                ", userID=" + userID +
                '}';
    }

    public Item DTO_to_Item() {
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setQuantity(quantity);
        item.setDate(date);
        item.setUnitID(unitID);
        item.setUserID(userID);
        return item;
    }

}
