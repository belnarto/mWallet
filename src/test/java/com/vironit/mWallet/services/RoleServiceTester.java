package com.vironit.mWallet.services;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.dao.RoleDao;
import com.vironit.mWallet.models.Role;
import com.vironit.mWallet.models.RoleEnum;


import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class RoleServiceTester {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleDao roleDao;

    private Role role;

    @Test
    public void constructorTest() {
        try {
            new RoleService();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void saveTest() {
        try {
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = roleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(roleService::delete);
            roleDao.save(role);
            roleService.delete(role);
        } catch (Exception e) {
            fail(e.getMessage());
            roleService.delete(role);
        }
    }

    @Test
    public void findByIdTest() {
        try {
            int id;
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = roleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(roleService::delete);
            roleDao.save(role);
            id = role.getId();
            assertEquals(roleService.findById(id), role);
            roleService.delete(role);
        } catch (Exception e) {
            fail(e.getMessage());
            roleService.delete(role);
        }
    }

    @Test
    public void updateTest() {
        try {
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = roleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(roleService::delete);
            roleDao.save(role);
            role.setRoleEnum(RoleEnum.TST);
            roleService.update(role);
            assertEquals(role.getRoleEnum().toString(), "TST");
            roleService.delete(role);
        } catch (Exception e) {
            fail(e.getMessage());
            roleService.delete(role);
        }
    }

    @Test
    public void deleteTest() {
        // TODO
    }

    @Test
    public void findAllTest() {
        //TODO
    }

}
