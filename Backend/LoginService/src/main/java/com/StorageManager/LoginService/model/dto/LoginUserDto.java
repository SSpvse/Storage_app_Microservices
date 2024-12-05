package com.StorageManager.LoginService.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginUserDto {

    private String email;
    private String password;
}
