package com.StorageApp.LoginService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;

    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }
    public UserDTO( String username, String email) {
        this.username = username;
        this.email = email;
    }


}
