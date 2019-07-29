package com.vironit.mwallet.service;

import com.vironit.mwallet.model.entity.Role;

import java.util.List;

public interface RoleService {

    Role findById(int id);

    Role findByName(String roleName);

    void save(Role role);

    void delete(Role role);

    void update(Role role);

    List<Role> findAll();

}
