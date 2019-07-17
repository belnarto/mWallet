package com.vironit.mwallet.services.converters;

import com.vironit.mwallet.models.Role;
import com.vironit.mwallet.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToRoleConverter implements Converter<String, Role> {

    @Autowired
    private RoleService roleService;

    public Role convert(String source) {
        return roleService.findByName(source);
    }
}
