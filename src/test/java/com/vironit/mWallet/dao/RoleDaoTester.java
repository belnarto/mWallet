package com.vironit.mWallet.dao;

import com.vironit.mWallet.models.Role;
import com.vironit.mWallet.models.RoleEnum;
import com.vironit.mWallet.services.RoleService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RoleDaoTester {

    private Role role;

    @Test
    void constructorTest() {
        try {
            new RoleDao();
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

            RoleDao.delete(role);
        } catch (Exception e) {
            fail(e.getMessage());
            RoleDao.delete(role);
        }

    }

    @Test
    void findAllTest() {
        try {
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = RoleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(RoleService::delete);
            RoleDao.save(role);

            List<Role> roles = RoleDao.findAll();

            assertTrue(roles.contains(role));

            RoleDao.delete(role);
        } catch (Exception e) {
            fail(e.getMessage());
            RoleDao.delete(role);
        }

    }

    @Test
    void findByIdTest() {
        try {
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = RoleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(RoleService::delete);
            RoleDao.save(role);

            Role role2 = RoleDao.findById(role.getId());

            assertEquals(role, role2);

            RoleDao.delete(role);
        } catch (Exception e) {
            fail(e.getMessage());
            RoleDao.delete(role);
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

            Role role2 = RoleDao.findById(role.getId());

            assertEquals(role, role2);

            role.setRoleEnum(RoleEnum.TST);
            RoleDao.update(role);

            assertEquals(role.getRoleEnum().toString(), "TST");

            RoleDao.delete(role);
        } catch (Exception e) {
            fail(e.getMessage());
            RoleDao.delete(role);
        }
    }

    @Test
    void deleteTest() {
        try {
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = RoleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(RoleService::delete);
            RoleDao.save(role);

            List<Role> roles = RoleDao.findAll();

            assertTrue(roles.contains(role));

            RoleDao.delete(role);

            roles = RoleDao.findAll();

            assertFalse(roles.contains(role));

        } catch (Exception e) {
            fail(e.getMessage());
            RoleDao.delete(role);
        }

    }

}
