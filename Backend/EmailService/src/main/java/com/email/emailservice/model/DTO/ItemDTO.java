package com.email.emailservice.model.DTO;

import com.email.emailservice.model.Item;
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
    private String date;
    private Long unitID;
    private Long userID;

    public Item DTO_to_Item() {
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setDescription(description);
        item.setQuantity(quantity);
        item.setDate(date);
        item.setUnitID(unitID);
        item.setUserID(userID);
        return item;
    }

}
