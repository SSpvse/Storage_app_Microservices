package com.StorageApp.UnitService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UnitUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    // OneToMany relationship to UnitUserAccess
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UnitUserAccess> unitUserAccessList;


    public UnitUser(Long id) {
        this.id = id;
    }
}
