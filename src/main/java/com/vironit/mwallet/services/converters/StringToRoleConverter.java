package com.vironit.mwallet.services.converters;

import com.vironit.mwallet.models.dto.RoleDto;
import com.vironit.mwallet.services.RoleService;
import com.vironit.mwallet.services.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class StringToRoleConverter implements Converter<String, RoleDto> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    public RoleDto convert(String source) {
        return roleMapper.toDto(roleService.findByName(source));
    }
}
