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
    public ResponseEntity<ItemDTO> addItem(@RequestBody ItemDTO itemDTO) {
        System.out.println("HERE IS THE ITEM :::::: " + itemDTO.toString());

        ItemDTO savedItem = itemService.addItem(itemDTO);
        System.out.println("FROM CONTROLLER: " + savedItem.toString());
        //return new ResponseEntity<>(savedItem.to_ItemDTO(), HttpStatus.CREATED);
        return ResponseEntity.ok(savedItem);

    }

    @PostMapping("/units/{unitId}/items")
    public ResponseEntity<ItemDTO> addItemToUnit(@PathVariable Long unitId, @RequestBody ItemDTO itemDTO){
        // Log the incoming request
        logger.info("Adding item to unit ID: {}", unitId);
        logger.info("Item details: {}", itemDTO);

        ItemDTO addedItem = itemService.addItemToUnit(unitId, itemDTO);

        // Returning the added item as respons
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    // ---- GET

    // get all items
    @GetMapping("/getall")
    public ResponseEntity<List<ItemDTO>> getAllItems() {

        List<ItemDTO> itemDTO_list = itemService.getAllItems();
        return new ResponseEntity<>(itemDTO_list, HttpStatus.OK);
    }

    // get item by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<ItemDTO> getItemBy_Id(@PathVariable Long id) {

        ItemDTO dto = itemService.getItemById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // get items_dtos by UNIT_ID
    @GetMapping("/byid/{unitId}")
    public ResponseEntity<List<ItemDTO>> getItemListBy_unitID(@PathVariable Long id) {

        List<ItemDTO> dto_list = itemService.getItemListBy_UnitID(id);
        return new ResponseEntity<>(dto_list, HttpStatus.OK);
    }

    // get items_dtos by ITEM_NAME
    @GetMapping("/get_name/{name}")
    public ResponseEntity<List<ItemDTO>> getItemListBy_name(@PathVariable String name) {

        List<ItemDTO> dto_list = itemService.getItemListBy_Name(name);
        return new ResponseEntity<>(dto_list, HttpStatus.OK);
    }

}
