package com.StorageApp.SearchService.Repository;

import com.StorageApp.SearchService.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<Item, Long> {



}
