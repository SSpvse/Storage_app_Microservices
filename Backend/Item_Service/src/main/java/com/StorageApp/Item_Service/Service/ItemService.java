package com.StorageApp.Item_Service.Service;

import com.StorageApp.Item_Service.Controller.ItemController;
import com.StorageApp.Item_Service.Model.DTO.DateDTO;
import com.StorageApp.Item_Service.Model.DTO.ItemDTO;
import com.StorageApp.Item_Service.Model.DTO.UnitDTO;
import com.StorageApp.Item_Service.Model.Item;
import com.StorageApp.Item_Service.Repository.ItemRepository;
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
    public Item addItem(ItemDTO itemDto) {

        if (itemDto == null) {
            throw new NullPointerException("Item cannot be null");
        }
        Item savedItem = null;

        try {
            if (itemDto.getUnit_id() != null) {
                // check for the unit in database to see if we have it
                String unitUrl = "http://localhost:8080/unit/get/" + itemDto.getUnit_id();
                ResponseEntity<UnitDTO> unitResponse = restTemplate.getForEntity(unitUrl, UnitDTO.class);
                // check the response of the DB
                if (unitResponse.getStatusCode().is2xxSuccessful()) {
                    UnitDTO existingUnit = unitResponse.getBody();
                    if (existingUnit != null) {
                        // if unit exists save the item so you can get the ID on it
                        System.out.println("EXISTING UNIT::: " + existingUnit.toString());
                        savedItem = itemRepository.save(itemDto.DTO_to_Item());// Save the item to the database first
                    } else {
                        throw new RuntimeException("--Unit not found, choose another!");
                    }
                } else {
                    throw new RuntimeException("Failed to retrieve unit from UnitService");
                }
            }
            if (itemDto.getDate() != null) {

                assert savedItem != null;
                DateDTO timeDto = new DateDTO(savedItem.getId(), savedItem.getDate(), savedItem.getUser_id());
                String notificationUrl = "http://localhost:8080/notification/addDto";
                ResponseEntity<String> response = restTemplate.postForEntity(notificationUrl, timeDto, String.class);
                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new RuntimeException("Failed to add notification to NotificationService");
                }
            }
            return savedItem;
        } catch (RestClientException e) {
            logger.error("Error adding item", e);
            throw e;
        }
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
    public List<ItemDTO> getItemListBy_UnitID(Long id) {

        List<ItemDTO> dtoList = new ArrayList<>();
        List<Item> itemList;
        itemList = itemRepository.findByUnitId(id);
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
