package com.StorageApp.ItemService.Controller;

import com.StorageApp.ItemService.Model.DTO.ItemDTO;
import com.StorageApp.ItemService.Model.Item;
import com.StorageApp.ItemService.Service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {


    @Autowired
    private ItemService itemService;

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    // ---- CREATE

    // add item
    @PostMapping("/additem")
    public ResponseEntity<ItemDTO> addItem(@RequestBody ItemDTO itemDTO) throws NoSuchMethodException {
        System.out.println("HERE IS THE ITEM :::::: " + itemDTO.toString());

        ItemDTO savedItem = itemService.addItem(itemDTO);
        System.out.println("FROM CONTROLLER: " + savedItem.toString());
        //return new ResponseEntity<>(savedItem.to_ItemDTO(), HttpStatus.CREATED);
        return ResponseEntity.ok(savedItem);

    }

    // ---- GET

    // get all items
    @GetMapping("/getall")
    public ResponseEntity<List<Item>> getAllItems() {

        List<Item> itemDTO_list = itemService.getAllItems();
        return new ResponseEntity<>(itemDTO_list, HttpStatus.OK);
    }

    // get item by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Item> getItemBy_Id(@PathVariable Long id) {

        Item dto = itemService.getItemById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // get items_dtos by UNIT_ID
    @GetMapping("/byid/{unitId}")
    public ResponseEntity<List<Item>> getItemListBy_unitID(@PathVariable Long unitId) {

        List<Item> dto_list = itemService.getItemListBy_UnitID(unitId);
        return new ResponseEntity<>(dto_list, HttpStatus.OK);
    }

    // get items_dtos by ITEM_NAME
    @GetMapping("/get_name/{name}")
    public ResponseEntity<List<Item>> getItemListBy_name(@PathVariable String name) {

        List<Item> dto_list = itemService.getItemListBy_Name(name);
        return new ResponseEntity<>(dto_list, HttpStatus.OK);
    }

    // update item by ID
    @PostMapping("/update/{id}")
    public ResponseEntity<Item> updateItem(@RequestBody Item itemDTO, @PathVariable Long id) {

        Item updatedItem = itemService.updateItem(itemDTO, id);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    // delete item by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
