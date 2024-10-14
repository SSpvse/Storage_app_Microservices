package com.StorageApp.Item_Service.Model.DTO;

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
    private LocalDate dateTime;
    private Long user_id;

}
