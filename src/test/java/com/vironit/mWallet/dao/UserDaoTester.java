package com.vironit.mWallet.dao;

import com.vironit.mWallet.models.Role;
import com.vironit.mWallet.models.RoleEnum;
import com.vironit.mWallet.services.RoleService;
import org.junit.jupiter.api.Test;
import com.vironit.mWallet.models.User;
import com.vironit.mWallet.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTester {

    private User user;
    private Role role;

    @Test
    void constructorTest() {
        try {
            new UserDao();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findAllTest() {
        try {
            user = new User("Test", "Test", "Test");
            Optional<User> userOpt = UserService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny();
            userOpt.ifPresent(UserService::delete);
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = RoleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(RoleService::delete);
            RoleService.save(role);
            user.setRole(role);
            User user2 = new User("Test2", "Test2", "Test2");
            Optional<User> userOpt2 = UserService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test2"))
                    .findAny();
            userOpt2.ifPresent(UserService::delete);
            user2.setRole(role);
            User user3 = new User("Test3", "Test3", "Test3");
            Optional<User> userOpt3 = UserService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test3"))
                    .findAny();
            userOpt3.ifPresent(UserService::delete);
            user3.setRole(role);

            List<User> usersToCheck = new ArrayList<>();
            usersToCheck.add(user);
            usersToCheck.add(user2);
            usersToCheck.add(user3);

            UserDao.save(user);
            UserDao.save(user2);
            UserDao.save(user3);

            List<User> users = UserDao.findAll();

            assertTrue(users.containsAll(usersToCheck));

            UserDao.delete(user);
            UserDao.delete(user2);
            UserDao.delete(user3);
        } catch (Exception e) {
            fail(e.getMessage());
            UserDao.delete(user);
        }

    }

    @Test
    void saveTest() {
        try {
            user = new User("Test", "Test", "Test");
            Optional<User> userOpt = UserService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny();
            userOpt.ifPresent(UserService::delete);
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = RoleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(RoleService::delete);
            RoleService.save(role);
            user.setRole(role);
            UserService.save(user);

            List<User> users = UserDao.findAll().stream()
                    .filter(u -> u.getName().equals("Test"))
                    .collect(Collectors.toList());

            assertTrue(users.contains(user));

            UserDao.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            UserDao.delete(user);
        }
    }

    @Test
    void findByIdTest() {
        try {
            user = new User("Test", "Test", "Test");
            Optional<User> userOpt = UserService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny();
            userOpt.ifPresent(UserService::delete);
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = RoleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(RoleService::delete);
            RoleService.save(role);
            user.setRole(role);
            UserService.save(user);

            assertEquals(UserDao.findById(user.getId()), user);

            UserDao.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            UserDao.delete(user);
        }
    }

    @Test
    void updateTest() {
        try {
            user = new User("Test", "Test", "Test");
            Optional<User> userOpt = UserService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny();
            userOpt.ifPresent(UserService::delete);
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = RoleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(RoleService::delete);
            RoleService.save(role);
            user.setRole(role);
            UserService.save(user);

            assertEquals(UserDao.findById(user.getId()).getName(), "Test");

            user.setName("Test2");
            UserDao.update(user);

            assertEquals(UserDao.findById(user.getId()).getName(), "Test2");

            UserDao.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            UserDao.delete(user);
        }
    }

    @Test
    void deleteTest() {
        try {
            user = new User("Test", "Test", "Test");
            Optional<User> userOpt = UserService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny();
            userOpt.ifPresent(UserService::delete);
            role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = RoleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            roleOpt.ifPresent(RoleService::delete);
            RoleService.save(role);
            user.setRole(role);
            UserService.save(user);

            assertNotNull(UserDao.findById(user.getId()));

            UserDao.delete(user);

            assertNull(UserDao.findById(user.getId()));
        } catch (Exception e) {
            fail(e.getMessage());
            UserDao.delete(user);
        }
    }
}
