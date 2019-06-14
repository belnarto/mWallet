package com.vironit.mWallet.services;

import com.vironit.mWallet.models.Role;
import com.vironit.mWallet.models.RoleEnum;
import org.junit.jupiter.api.Test;
import com.vironit.mWallet.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTester {

    private User user;
    private int id;
    private Role role;

    @Test
    void constructorTest() {
        try {
            new UserService();
        } catch (Exception e) {
            fail(e.getMessage());
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
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            UserService.delete(user);
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
            id = user.getId();
            assertEquals(UserService.findById(id),user);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            UserService.delete(user);
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
            id = user.getId();
            user.setName("Test3");
            UserService.update(user);
            assertEquals(UserService.findById(id).getName(),"Test3");
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            UserService.delete(user);
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
            id = user.getId();
            UserService.delete(user);
            assertNull(UserService.findById(id));
        } catch (Exception e) {
            fail(e.getMessage());
            UserService.delete(user);
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
            User user2 = new User("Test2","Test2","Test2");
            user2.setRole(role);
            User user3 = new User("Test3","Test3","Test3");
            user3.setRole(role);
            List<User> usersToCheck = new ArrayList<>();
            usersToCheck.add(user);
            usersToCheck.add(user2);
            usersToCheck.add(user3);
            UserService.save(user);
            UserService.save(user2);
            UserService.save(user3);
            List<User> users = UserService.findAll();
            assertTrue(users.containsAll(usersToCheck));
            UserService.delete(user);
            UserService.delete(user2);
            UserService.delete(user3);
        } catch (Exception e) {
            fail(e.getMessage());
            UserService.delete(user);
        }

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
            User user2 = new User("Test2","Test2","Test2");
            user2.setRole(role);
            User user3 = new User("Test3","Test3","Test3");
            user3.setRole(role);
            List<User> usersToCheck = new ArrayList<>();
            usersToCheck.add(user);
            usersToCheck.add(user2);
            usersToCheck.add(user3);
            UserService.save(user);
            UserService.save(user2);
            //UserService.save(user3);
            List<User> users = UserService.findAll();
            assertFalse(users.containsAll(usersToCheck));
            UserService.delete(user);
            UserService.delete(user2);
            UserService.delete(user3);
        } catch (Exception e) {
            fail(e.getMessage());
            UserService.delete(user);
        }
    }

}
