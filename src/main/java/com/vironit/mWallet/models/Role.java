package com.vironit.mWallet.models;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Data
public class Role {

    public Role(RoleEnum roleEnum) {
        setRoleEnum(roleEnum);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PositiveOrZero(message = "Can't be negative")
    private int id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Can't be null")
    private RoleEnum roleEnum;

}