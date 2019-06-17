package com.vironit.mWallet.services;

import com.vironit.mWallet.dao.RoleDao;
import com.vironit.mWallet.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private RoleDao roleDao;

    public RoleService() {
    }

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Role findById(int id) {
        return roleDao.findById(id);
    }

    public Role findByName(String roleName) {
        return roleDao.findByName(roleName);
    }

    public void save(Role driver) {
        roleDao.save(driver);
    }

    public void delete(Role driver) {
        roleDao.delete(driver);
    }

    public void update(Role driver) {
        roleDao.update(driver);
    }

    public List<Role> findAll() {
        return roleDao.findAll();
    }

}
