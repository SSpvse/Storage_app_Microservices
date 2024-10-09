package com.StorageApp.Item_Service.Repository;

import com.StorageApp.Item_Service.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByUnitId(Long unitID);
    List<Item> findByName(String name);
    List<Item> findByNameContaining(String name);
}
