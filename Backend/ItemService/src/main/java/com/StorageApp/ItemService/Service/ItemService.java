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
                DateDTO timeDto = new DateDTO(savedItem.getId(),savedItem.getName(), savedItem.getDate(), savedItem.getUserID(), savedItem.getUnitID());

                System.out.println("BEFORE SENDING TO THE RESTTEMPLATE POSTFORENTTITY, timeDto.getDate:::: " + timeDto.getDate());
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
    public Item updateItem(Item item, Long id) {
        Optional<Item> oldItem = itemRepository.findById(id);
        if (oldItem.isPresent()) {
            itemRepository.deleteById(id);

            return itemRepository.save(item);
        }else {
            return null;
        }
    }

    // delete item by id

    @Transactional
    public void deleteItem(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isEmpty()) {
            throw new RuntimeException("Item not found with ID: " + id);
        }else {
            itemRepository.deleteById(id);
        }
    }



}
