package com.vironit.mwallet.services.mapper;

import com.vironit.mwallet.models.dto.RoleDto;
import com.vironit.mwallet.models.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RoleMapper {

    @Autowired
    private ModelMapper mapper;

    public Role toEntity(RoleDto roleDto) {
        return Objects.isNull(roleDto) ? null : mapper.map(roleDto, Role.class);
    }

    public RoleDto toDto(Role roleEntity) {
        return Objects.isNull(roleEntity) ? null : mapper.map(roleEntity, RoleDto.class);
    }
}