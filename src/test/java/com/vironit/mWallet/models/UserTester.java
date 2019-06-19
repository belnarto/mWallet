package com.vironit.mWallet.models;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.services.RoleService;
import com.vironit.mWallet.services.UserService;

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
public class UserTester {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private User user;
    private int id;
    private Role role;

    @Test
    public void constructorTest() {
        try {
            new User();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            role = new Role(RoleEnum.TST);
            new User.UserBuilder()
                    .setName("Test")
                    .setLogin("Test")
                    .setPassword("Test")
                    .setRole(role)
                    .build();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getIdTest() {
        try {
            Optional<User> userOpt = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny();
            userOpt.ifPresent(userService::delete);
            Optional<Role> roleOpt = roleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            role = new Role(RoleEnum.TST);
            user = new User.UserBuilder()
                    .setName("Test")
                    .setLogin("Test")
                    .setPassword("Test")
                    .setRole(roleOpt.orElse(role))
                    .build();
            if (!roleOpt.isPresent()) {
                roleService.save(role);
            }
            userService.save(user);

            id = user.getId();
            assertTrue(id > 0);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            userService.delete(user);
        }
    }

    @Test
    public void setAndGetNameTest() {
        try {
            Optional<User> userOpt = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny();
            userOpt.ifPresent(userService::delete);
            Optional<Role> roleOpt = roleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            role = new Role(RoleEnum.TST);
            user = new User.UserBuilder()
                    .setName("Test")
                    .setLogin("Test")
                    .setPassword("Test")
                    .setRole(roleOpt.orElse(role))
                    .build();
            if (!roleOpt.isPresent()) {
                roleService.save(role);
            }
            userService.save(user);

            id = user.getId();
            user.setName("Test2");
            userService.update(user);
            boolean result = userService.findAll()
                    .stream()
                    .filter(u -> u.getId() == id)
                    .findFirst()
                    .orElseThrow(null)
                    .getName()
                    .equals("Test2");
            assertTrue(result);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            userService.delete(user);
        }
    }

    @Test
    public void equalsTest() {
        User user2;
        try {
            Optional<User> userOpt = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny();
            userOpt.ifPresent(userService::delete);
            Optional<Role> roleOpt = roleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            role = new Role(RoleEnum.TST);
            user = new User.UserBuilder()
                    .setName("Test")
                    .setLogin("Test")
                    .setPassword("Test")
                    .setRole(roleOpt.orElse(role))
                    .build();
            if (!roleOpt.isPresent()) {
                roleService.save(role);
            }
            userService.save(user);

            userOpt = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test2"))
                    .findAny();
            userOpt.ifPresent(userService::delete);
            user2 = new User.UserBuilder()
                    .setName("Test2")
                    .setLogin("Test2")
                    .setPassword("Test2")
                    .setRole(roleOpt.orElse(role))
                    .build();
            userService.save(user2);

            assertNotEquals(user, user2);
        } catch (Exception e) {
            fail(e.getMessage());
            userService.delete(user);
        }

        try {
            Optional<User> userOpt = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny();
            userOpt.ifPresent(userService::delete);
            Optional<Role> roleOpt = roleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            role = new Role(RoleEnum.TST);
            user = new User.UserBuilder()
                    .setName("Test")
                    .setLogin("Test")
                    .setPassword("Test")
                    .setRole(roleOpt.orElse(role))
                    .build();
            if (!roleOpt.isPresent()) {
                roleService.save(role);
            }
            userService.save(user);

            user2 = user;
            assertEquals(user, user2);
        } catch (Exception e) {
            fail(e.getMessage());
            userService.delete(user);
        }
    }

}
