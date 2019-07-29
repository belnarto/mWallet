package com.vironit.mwallet.dao;

import com.vironit.mwallet.model.entity.Role;

public interface RoleDao extends CrudDao<Role> {

    Role findByName(String roleName);

}
