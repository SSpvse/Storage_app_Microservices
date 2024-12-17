package com.StorageApp.UnitService.model.dto;

import com.StorageApp.UnitService.model.Role;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InviteGuestDTO {

    private Long unitID;
    private Long ownerID;
    private String guestEmail;
    private Role role;

    @Override
    public String toString() {
        return "InviteGuestDTO{" +
                "unitID=" + unitID +
                ", ownerID=" + ownerID +
                ", guestEmail='" + guestEmail + '\'' +
                ", role=" + role +
                '}';
    }
}
