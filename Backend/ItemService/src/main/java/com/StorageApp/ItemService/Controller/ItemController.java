package com.StorageApp.ItemService.Controller;

import com.StorageApp.ItemService.Model.DTO.ItemDTO;
import com.StorageApp.ItemService.Model.Item;
import com.StorageApp.ItemService.Service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        System.out.println("Recived Item DTO in itemcontroller" + itemDTO.toString());

        try {
            //  If the date is provided as a string, convert it to LocalDate
            if (itemDTO.getDate() != null && itemDTO.getDate().toString().matches("\\d{4}-\\d{2}-\\d{2}")) {
                LocalDate date = LocalDate.parse(itemDTO.getDate().toString(), DateTimeFormatter.ISO_DATE);
                itemDTO.setDate(date);
            }

            ItemDTO savedItem = itemService.addItem(itemDTO);
            System.out.println("FROM CONTROLLER: saved item:" + savedItem.toString());
            //return new ResponseEntity<>(savedItem.DTO_to_Item().to_ItemDTO(), HttpStatus.CREATED);
            return ResponseEntity.ok(savedItem);

        } catch (Exception e){
            logger.error("Error occurred while adding item: ",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    /*
    @PostMapping("/add/{unitId}")
    public ResponseEntity<ItemDTO> addItemToUnit(@PathVariable Long unitId, @RequestBody ItemDTO itemDTO) {
        try {
            // Set the unitId in the itemDTO and save it using the service
            itemDTO.setUnitID(unitId);
            ItemDTO savedItem = itemService.addItem(itemDTO);

            // Return the saved item as a response
            return ResponseEntity.ok(savedItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }*/

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
    public ResponseEntity<List<ItemDTO>> getItemListBy_unitID(@PathVariable Long unitId) {

        List<ItemDTO> dto_list = itemService.getItemListBy_UnitID(unitId);
        return new ResponseEntity<>(dto_list, HttpStatus.OK);
    }

    // get items_dtos by ITEM_NAME
    @GetMapping("/get_name/{name}")
    public ResponseEntity<List<ItemDTO>> getItemListBy_name(@PathVariable String name) {

        List<ItemDTO> dto_list = itemService.getItemListBy_Name(name);
        return new ResponseEntity<>(dto_list, HttpStatus.OK);
    }

}
