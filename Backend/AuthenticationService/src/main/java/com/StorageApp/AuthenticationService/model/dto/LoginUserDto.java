package com.StorageApp.AuthenticationService.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDto {
    private String email;

    private String password;

}