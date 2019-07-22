package com.vironit.mwallet.models.dto;

import com.vironit.mwallet.models.attributes.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDto {

    @PositiveOrZero(message = "Can't be negative")
    private int id;

    @NotNull(message = "Can't be null")
    private RoleEnum roleEnum;

    @Override
    public String toString() {
        return roleEnum.toString();
    }
}
