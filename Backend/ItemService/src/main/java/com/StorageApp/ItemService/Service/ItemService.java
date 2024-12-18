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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public ItemDTO addItem(ItemDTO itemDto) throws NoSuchMethodException {

        if (itemDto == null) {
            throw new NullPointerException("Item is null");
        }

        Method getNameMethod = itemDto.getClass().getMethod("getName");
        Method getDateMethod = itemDto.getClass().getMethod("getDate");
        Method getUserIDMethod = itemDto.getClass().getMethod("getUserID");
        Method getUnitIDMethod = itemDto.getClass().getMethod("getUnitID");

        // Print the data types of each field
        System.out.println("~ : ~ : ~ : C H E C K I N G  --- T H E  --- I T E M  ---  F R O M   C- AL ALLL   ~ : ~ : ~ : ");
        System.out.println("HERE::::: " +
                itemDto.getName() + " (" + getNameMethod.getReturnType().getName() + ") " +
                itemDto.getDate() + " (" + getDateMethod.getReturnType().getName() + ") " +
                itemDto.getUserID() + " (" + getUserIDMethod.getReturnType().getName() + ") " +
                itemDto.getUnitID() + " (" + getUnitIDMethod.getReturnType().getName() + ")");


        System.out.println(" END ---------- END ----------- END ----------------END");
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
                // System.out.println("INSIDE ITEM_SERVICE / ADDITEM METHOD / itemDto.getDate != null :: getdate::" + savedItem.getDate());

                assert savedItem != null;
                DateDTO timeDto = new DateDTO(
                        savedItem.getId(),
                        savedItem.getName(),
                        savedItem.getDate(),
                        savedItem.getUnitID(),
                        savedItem.getUserID()
                );

                System.out.println("BEFORE SENDING TO THE RESTTEMPLATE POSTFORENTTITY, timeDto.getDate:::: " + timeDto.getDate());
                System.out.println("BEFORE SENDING TO THE RESTTEMPLATE POSTFORENTTITY, timeDto.getUnitID:::: " + timeDto.getUnitID());
                System.out.println("BEFORE SENDING TO THE RESTTEMPLATE POSTFORENTTITY, timeDto.getUserID:::: " + timeDto.getUserID());
              // for localhost :
                //String notificationUrl = "http://localhost:8000/notification/add";
                String notificationUrl = "http://gateway:8000/notification/add";
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

    // ---- GET ITEMS

    // get all items_dto
    @Transactional
    public List<Item> getAllItems() {
        List<Item> dtoList = new ArrayList<>();
        List<Item> itemList = itemRepository.findAll();
        for (Item item : itemList) {
            Item itemDto = item;
            dtoList.add(itemDto);
        }
        return dtoList;
    }

    // get itemDto by ID
    public Item getItemById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return item.get(); // If a value is present, returns the value, otherwise throws NoSuchElementException.
        }
        return null;
    }

    // get item_dto LIST by Unit_ID
    public List<Item> getItemListBy_UnitID(Long unitId) {

        List<Item> dtoList = new ArrayList<>();
        List<Item> itemList;
        itemList = itemRepository.findByUnitID(unitId);
        if (itemList.isEmpty()) {
            return null;
        }
        for (Item item : itemList) {
            Item itemDto = item;
            dtoList.add(itemDto);
        }
        return dtoList;
    }

    // get item_dto LIST by Item_NAME
    public List<Item> getItemListBy_Name(String name) {

        List<Item> itemList = itemRepository.findByNameContaining(name);
        List<Item> dtoList = new ArrayList<>();
        for (Item item : itemList) {
            Item itemDto = item;
            dtoList.add(itemDto);
        }
        return dtoList;
    }

    // update item
    @Transactional
    public Item updateItem(Item item , Long id) {
        System.out.println("INSIDE UPDATE ITEM SERVICE");
        System.out.println("ITEM ID: " + item.getId());
        System.out.println("ITEM NAME: " + item.getName());
        System.out.println("ITEM DATE: " + item.getDate());
        System.out.println("ITEM USER ID: " + item.getUserID());
        System.out.println("ITEM UNIT ID: " + item.getUnitID());

        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item with ID " + item.getId() + " not found"));

        System.out.println(" -x-x-x-x-x-x-x   EXISTING ITEM ID: " + existingItem.getId() + " -x-x-x-x-x-x-x  ");

        if (item.getName() != null && !item.getName().isEmpty()) {
            existingItem.setName(item.getName());
        }
        if (item.getDate() != null) {
            existingItem.setDate(item.getDate());
        }
        if (item.getUserID() != null) {
            existingItem.setUserID(item.getUserID());
        }
        if (item.getUnitID() != null) {
            existingItem.setUnitID(item.getUnitID());
        }

        return itemRepository.save(existingItem);
    }

    // delete item by id

    @Transactional
    public void deleteItem(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isEmpty()) {
            throw new RuntimeException("Item not found with ID: " + id);
        }else {
            restTemplate.delete("http://gateway:8000/notification/delete/" + id);
            itemRepository.deleteById(id);
        }
    }



}
