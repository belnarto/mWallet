package com.vironit.mWallet.dao;

import com.vironit.mWallet.models.Role;
import org.springframework.stereotype.Repository;

public interface RoleDao extends CrudDao<Role> {

    Role findByName(String roleName);

}
