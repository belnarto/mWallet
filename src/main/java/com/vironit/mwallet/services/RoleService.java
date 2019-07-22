package com.vironit.mwallet.services;

import com.vironit.mwallet.models.entity.Role;

import java.util.List;

public interface RoleService {

    Role findById(int id);

    Role findByName(String roleName);

    void save(Role role);

    void delete(Role role);

    void update(Role role);

    List<Role> findAll();

}
