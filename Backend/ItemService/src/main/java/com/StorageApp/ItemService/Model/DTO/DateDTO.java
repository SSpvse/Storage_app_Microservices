package com.StorageApp.ItemService.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DateDTO {

    private Long id;
    private String name;
    private LocalDate date;
    private Long unitID;
    private Long userID;
}
