package com.vironit.mWallet.services;

import com.vironit.mWallet.dao.RoleDao;
import com.vironit.mWallet.models.Role;
import com.vironit.mWallet.models.RoleEnum;
import org.junit.jupiter.api.Test;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RoleServiceTester {

    private Role role;

    @Test
    void constructorTest() {
        try {
            new RoleService();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void saveTest() {
        try {
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = RoleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(RoleService::delete);
            RoleDao.save(role);
            RoleService.delete(role);
        } catch (Exception e) {
            fail(e.getMessage());
            RoleService.delete(role);
        }
    }

    @Test
    void findByIdTest() {
        try {
            int id;
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = RoleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(RoleService::delete);
            RoleDao.save(role);
            id = role.getId();
            assertEquals(RoleService.findById(id), role);
            RoleService.delete(role);
        } catch (Exception e) {
            fail(e.getMessage());
            RoleService.delete(role);
        }
    }

    @Test
    void updateTest() {
        try {
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = RoleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(RoleService::delete);
            RoleDao.save(role);
            role.setRoleEnum(RoleEnum.TST);
            RoleService.update(role);
            assertEquals(role.getRoleEnum().toString(), "TST");
            RoleService.delete(role);
        } catch (Exception e) {
            fail(e.getMessage());
            RoleService.delete(role);
        }
    }

    @Test
    void deleteTest() {
        // TODO
    }

    @Test
    void findAllTest() {
        //TODO
    }

}
