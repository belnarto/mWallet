package com.vironit.mwallet.services;

import com.vironit.mwallet.models.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto findById(int id);

    RoleDto findByName(String roleName);

    void save(RoleDto roleDto);

    void delete(RoleDto roleDto);

    void update(RoleDto roleDto);

    List<RoleDto> findAll();

}
