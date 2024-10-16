package com.StorageApp.Item_Service.Controller;

import com.StorageApp.Item_Service.Model.DTO.ItemDTO;
import com.StorageApp.Item_Service.Model.Item;
import com.StorageApp.Item_Service.Service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {


    @Autowired
    private ItemService _itemService;

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    /*
    // TESTING
    @GetMapping("/testUnit/{id}")
    public ResponseEntity<Unit> getUnitTest(@PathVariable Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String unitUrl = "http://localhost:8080/unit/get/" + id;
        ResponseEntity<UnitDto> unitResponse = restTemplate.getForEntity(unitUrl, UnitDto.class);
        UnitDto existingUnit = unitResponse.getBody();
        return new ResponseEntity<>(MapperUnit.dtoToUnit(existingUnit), HttpStatus.OK);
    }

     */


    // ---- CREATE

    // add item
    @PostMapping("/add_item")
    public ResponseEntity<ItemDTO> addItem(@RequestBody ItemDTO item_dto) {
        System.out.println("ACCESSING API");

        Item savedItem = _itemService.addItem(item_dto);
        System.out.println("FROM CONTROLLER: " + savedItem.toString());
        return new ResponseEntity<>(savedItem.to_ItemDTO(), HttpStatus.CREATED);

    }

    // ---- GET

    // get all items
    @GetMapping("/getall")
    public ResponseEntity<List<ItemDTO>> getAllItems() {

        List<ItemDTO> itemDTO_list = _itemService.getAllItems();
        return new ResponseEntity<>(itemDTO_list, HttpStatus.OK);
    }

    // get item by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<ItemDTO> getItemBy_Id(@PathVariable Long id) {

        ItemDTO dto = _itemService.getItemById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // get items_dtos by UNIT_ID
    @GetMapping("/get_unitID/{id}")
    public ResponseEntity<List<ItemDTO>> getItemListBy_unitID(@PathVariable Long id) {

        System.out.println("!!!! --- getlist by unit id");
        List<ItemDTO> dto_list = _itemService.getItemListBy_UnitID(id);
        return new ResponseEntity<>(dto_list, HttpStatus.OK);
    }

    // get items_dtos by ITEM_NAME
    @GetMapping("/get_name/{name}")
    public ResponseEntity<List<ItemDTO>> getItemListBy_name(@PathVariable String name) {

        List<ItemDTO> dto_list = _itemService.getItemListBy_Name(name);
        return new ResponseEntity<>(dto_list, HttpStatus.OK);
    }

}
