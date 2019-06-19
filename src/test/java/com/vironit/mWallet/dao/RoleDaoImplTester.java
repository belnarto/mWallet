package com.vironit.mWallet.dao;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.dao.impl.RoleDaoImpl;
import com.vironit.mWallet.models.Role;
import com.vironit.mWallet.models.RoleEnum;
import com.vironit.mWallet.services.RoleService;
import com.vironit.mWallet.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class RoleDaoImplTester {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleDao roleDao;

    private Role role;


    @Test
    public void constructorTest() {
        try {
            new RoleDaoImpl();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void saveTest() {
        try {
            Optional<Role> roleOpt = roleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            userService.findAll().stream()
                    .filter(user -> user.getRole().getRoleEnum().toString().equals("TST"))
                    .forEach(userService::delete);
            roleOpt.ifPresent(roleService::delete);
            role = new Role(RoleEnum.TST);
            roleDao.save(role);

            roleDao.delete(role);
        } catch (Exception e) {
            fail(e.getMessage());
            roleDao.delete(role);
        }

    }

    @Test
    public void findAllTest() {
        try {
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = roleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(roleService::delete);
            roleDao.save(role);

            List<Role> roles = roleDao.findAll();

            assertTrue(roles.contains(role));

            roleDao.delete(role);
        } catch (Exception e) {
            fail(e.getMessage());
            roleDao.delete(role);
        }

    }

    @Test
    public void findByIdTest() {
        try {
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = roleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(roleService::delete);
            roleDao.save(role);

            Role role2 = roleDao.findById(role.getId());

            assertEquals(role, role2);

            roleDao.delete(role);
        } catch (Exception e) {
            fail(e.getMessage());
            roleDao.delete(role);
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

            Role role2 = roleDao.findById(role.getId());

            assertEquals(role, role2);

            role.setRoleEnum(RoleEnum.TST);
            roleDao.update(role);

            assertEquals(role.getRoleEnum().toString(), "TST");

            roleDao.delete(role);
        } catch (Exception e) {
            fail(e.getMessage());
            roleDao.delete(role);
        }
    }

    @Test
    public void deleteTest() {
        try {
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = roleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(roleService::delete);
            roleDao.save(role);

            List<Role> roles = roleDao.findAll();

            assertTrue(roles.contains(role));

            roleDao.delete(role);

            roles = roleDao.findAll();

            assertFalse(roles.contains(role));

        } catch (Exception e) {
            fail(e.getMessage());
            roleDao.delete(role);
        }

    }

}
