package com.vironit.mwallet.service.mapper;

import com.vironit.mwallet.model.dto.RoleDto;
import com.vironit.mwallet.model.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class RoleMapper {

    @Autowired
    private ModelMapper mapper;

    @SuppressWarnings("unused")
    public Role toEntity(RoleDto roleDto) {
        return Objects.isNull(roleDto) ? null : mapper.map(roleDto, Role.class);
    }

    public RoleDto toDto(Role roleEntity) {
        return Objects.isNull(roleEntity) ? null : mapper.map(roleEntity, RoleDto.class);
    }
}