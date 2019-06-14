package com.vironit.mWallet.services;

import com.vironit.mWallet.dao.RoleDao;
import com.vironit.mWallet.models.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    public RoleService() {
    }

    public static Role findById(int id) {
        return RoleDao.findById(id);
    }

    public static void save(Role driver) {
        RoleDao.save(driver);
    }

    public static void delete(Role driver) {
        RoleDao.delete(driver);
    }

    public static void update(Role driver) {
        RoleDao.update(driver);
    }

    public static List<Role> findAll() {
        return RoleDao.findAll();
    }

}
