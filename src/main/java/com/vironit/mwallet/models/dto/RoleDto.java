package com.vironit.mwallet.models.dto;

import com.vironit.mwallet.models.attributes.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDto {

    private int id;
    private RoleEnum roleEnum;

    @Override
    public String toString() {
        return roleEnum.toString();
    }
}
