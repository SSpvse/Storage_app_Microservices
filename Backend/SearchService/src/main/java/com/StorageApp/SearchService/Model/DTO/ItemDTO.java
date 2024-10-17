package com.StorageApp.SearchService.Model.DTO;

import com.StorageApp.SearchService.Model.Item;
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

    private Long id;
    private String name;
    private String description;
    private Long quantity;
    private LocalDate date;
    private Long unit_id;

    public Item DTO_to_Item() {
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setDescription(description);
        item.setQuantity(quantity);
        item.setDate(date);
        item.setUnitID(unit_id);
        return item;
    }

}
