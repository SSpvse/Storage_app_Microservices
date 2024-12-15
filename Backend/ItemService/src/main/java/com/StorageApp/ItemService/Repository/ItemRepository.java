package com.StorageApp.ItemService.Repository;

import com.StorageApp.ItemService.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByUnitID(Long unitID);
    Item findById(long id);
    List<Item> findByName(String name);
    List<Item> findByNameContaining(String name);
    void deleteById(Long id);



}
