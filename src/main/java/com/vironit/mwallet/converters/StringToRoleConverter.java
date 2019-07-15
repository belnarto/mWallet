package com.vironit.mwallet.converters;

import com.vironit.mwallet.models.Role;
import com.vironit.mwallet.services.RoleService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToRoleConverter implements Converter<String, Role> {

    private RoleService roleService;

    public StringToRoleConverter(RoleService roleService) {
        this.roleService = roleService;
    }


    public Role convert(String source) {
        return roleService.findByName(source);
    }
}
