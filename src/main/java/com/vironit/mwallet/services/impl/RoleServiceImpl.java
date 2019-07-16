package com.vironit.mwallet.services.impl;

import com.vironit.mwallet.dao.RoleDao;
import com.vironit.mwallet.models.Role;
import com.vironit.mwallet.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role findById(int id) {
        return roleDao.findById(id);
    }

    public Role findByName(String roleName) {
        return roleDao.findByName(roleName);
    }

    public void save(Role role) {
        roleDao.save(role);
    }

    public void delete(Role role) {
        roleDao.delete(role);
    }

    public void update(Role role) {
        roleDao.update(role);
    }

    public List<Role> findAll() {
        return roleDao.findAll();
    }

}
