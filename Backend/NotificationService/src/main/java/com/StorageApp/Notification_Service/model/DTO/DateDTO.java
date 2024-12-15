package com.StorageApp.Notification_Service.model.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class DateDTO {

    @Id
    private Long id;
    private String name;
    private LocalDate date;
    private Long unitID;
    private Long userID;

}
