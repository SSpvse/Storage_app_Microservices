package com.StorageApp.ItemService.Service;

import com.StorageApp.ItemService.Controller.ItemController;
import com.StorageApp.ItemService.Model.DTO.DateDTO;
import com.StorageApp.ItemService.Model.DTO.ItemDTO;
import com.StorageApp.ItemService.Model.Item;
import com.StorageApp.ItemService.Repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@NoArgsConstructor
public class ItemService {



    @Autowired
    ItemRepository itemRepository;

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final RestTemplate restTemplate = new RestTemplate();


    // ---- ADD ITEMS

    // add item
    @Transactional
    public ItemDTO addItem(ItemDTO itemDto) {


        if (itemDto == null) {
            throw new NullPointerException("Item is null");
        }
        Item savedItem = null;

        try {
            if (itemDto.getUnitID() != null) {
                // check for the unit in database to see if we have it
                // String unitUrl = "http://localhost:8081/unit/exists/" + itemDto.getUnitID();

                String unitUrl = "http://gateway:8000/unit/exists/" + itemDto.getUnitID();
                ResponseEntity<Boolean> unitResponse = restTemplate.getForEntity(unitUrl, Boolean.class);
                // check the response of the DB
                if (unitResponse.getStatusCode().is2xxSuccessful()) {
                    Boolean existingUnit = unitResponse.getBody();
                    System.out.println("THIS HERE IS THE EXISTING UNIT::: ------: :::: " + existingUnit);
                    if (existingUnit == true) {
                        System.out.println("THIS IS THE TO STRING ITEM DTO INSIDE THE ITEM SERVICE::: ");
                        itemDto.toString();
                        Item item = itemDto.DTO_to_Item();
                        savedItem = itemRepository.save(item);// Save the item to the database first
                    } else {
                        throw new RuntimeException("Could not save item to database! ");
                    }
                } else {
                    throw new RuntimeException("Failed to retrieve unit from UnitService");
                }
            }
            if (itemDto.getDate() != null) {
                System.out.println("INSIDE ITEM_SERVICE / ADDITEM METHOD / itemDto.getDate != null :: getdate::" + savedItem.getDate());

                assert savedItem != null;
                DateDTO timeDto = new DateDTO(savedItem.getId(),savedItem.getName(), savedItem.getDate(), savedItem.getUserID(), savedItem.getUnitID());

                System.out.println("BEFORE SENDING TO THE RESTTEMPLATE POSTFORENTTITY, timeDto.getDate:::: " + timeDto.getDate());
                String notificationUrl = "http://localhost:8000/notification/add";
                ResponseEntity<String> response = restTemplate.postForEntity(notificationUrl, timeDto, String.class);
                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new RuntimeException("Failed to add dateDTO  to notification/add ");
                }
            }
            return savedItem.to_ItemDTO();
        } catch (RestClientException e) {
            logger.error("Error adding item", e);
            throw e;
        }
    }

    // Add item to a unit by the unitId
    public ItemDTO addItemToUnit(Long unitId, ItemDTO itemDTO){
        if (itemDTO.getUnitID() == null){
            throw new NullPointerException("Unit is null");
        }

        // Convert ItemDTO to Item
        Item item = itemDTO.DTO_to_Item();

        // Setting the unitID
        item.setUnitID(unitId);

        Item savedItem = itemRepository.save(item);

        // Converting back to DTO to return
        return savedItem.to_ItemDTO();


    }

    // ---- GET ITEMS

    // get all items_dto
    @Transactional
    public List<ItemDTO> getAllItems() {
        List<ItemDTO> dtoList = new ArrayList<>();
        List<Item> itemList = itemRepository.findAll();
        for (Item item : itemList) {
            ItemDTO itemDto = item.to_ItemDTO();
            dtoList.add(itemDto);
        }
        return dtoList;
    }

    // get itemDto by ID
    public ItemDTO getItemById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return item.get().to_ItemDTO(); // If a value is present, returns the value, otherwise throws NoSuchElementException.
        }
        return null;
    }

    // get item_dto LIST by Unit_ID
    public List<ItemDTO> getItemListBy_UnitID(Long unitId) {

        List<ItemDTO> dtoList = new ArrayList<>();
        List<Item> itemList;
        itemList = itemRepository.findByUnitID(unitId);
        if (itemList.isEmpty()) {
            return null;
        }
        for (Item item : itemList) {
            ItemDTO itemDto = item.to_ItemDTO();
            dtoList.add(itemDto);
        }
        return dtoList;
    }

    // get item_dto LIST by Item_NAME
    public List<ItemDTO> getItemListBy_Name(String name) {

        List<Item> itemList = itemRepository.findByNameContaining(name);
        List<ItemDTO> dtoList = new ArrayList<>();
        for (Item item : itemList) {
            ItemDTO itemDto = item.to_ItemDTO();
            dtoList.add(itemDto);
        }
        return dtoList;
    }
}
