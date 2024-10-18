package com.email.emailservice.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
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

   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String date;
    private Long userID;

}
