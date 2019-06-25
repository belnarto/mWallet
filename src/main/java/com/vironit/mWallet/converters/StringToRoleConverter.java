package com.vironit.mWallet.converters;

import com.vironit.mWallet.models.Role;
import com.vironit.mWallet.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
