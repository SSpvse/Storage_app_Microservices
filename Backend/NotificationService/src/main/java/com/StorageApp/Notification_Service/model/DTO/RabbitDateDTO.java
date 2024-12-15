package com.StorageApp.Notification_Service.model.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RabbitDateDTO {

    private Long id;
    private String name;
    private String date;
    private Long unitID;
}
