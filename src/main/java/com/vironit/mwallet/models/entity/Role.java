package com.vironit.mwallet.models.entity;

import com.vironit.mwallet.models.attributes.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PositiveOrZero(message = "Can't be negative")
    private int id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Can't be null")
    private RoleEnum roleEnum;

    @Override
    public String toString() {
        return roleEnum.toString();
    }
}