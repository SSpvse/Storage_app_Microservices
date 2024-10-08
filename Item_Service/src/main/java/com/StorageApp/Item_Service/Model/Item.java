package com.StorageApp.Item_Service.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    private Long quantity;

    // @JsonFormat
    private LocalDate date;

    @Column(nullable = false)
    private Long Unit_ID;

}
