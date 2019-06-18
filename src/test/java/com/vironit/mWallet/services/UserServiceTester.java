package com.vironit.mWallet.services;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.models.Role;
import com.vironit.mWallet.models.RoleEnum;
import com.vironit.mWallet.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class UserServiceTester {

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
            new UserService();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void saveTest() {
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

            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            userService.delete(user);
        }
    }

    @Test
    public void findByIdTest() {
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
            assertEquals(userService.findById(id), user);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            userService.delete(user);
        }
    }

    @Test
    public void updateTest() {
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
            user.setName("Test3");
            userService.update(user);
            assertEquals(userService.findById(id).getName(), "Test3");
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            userService.delete(user);
        }
    }

    @Test
    public void deleteTest() {
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
            userService.delete(user);
            assertNull(userService.findById(id));
        } catch (Exception e) {
            fail(e.getMessage());
            userService.delete(user);
        }
    }

    @Test
    public void findAllTest() {
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

            userOpt = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test2"))
                    .findAny();
            userOpt.ifPresent(userService::delete);
            User user2 = new User.UserBuilder()
                    .setName("Test2")
                    .setLogin("Test2")
                    .setPassword("Test2")
                    .setRole(roleOpt.orElse(role))
                    .build();

            userOpt = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test3"))
                    .findAny();
            userOpt.ifPresent(userService::delete);
            User user3 = new User.UserBuilder()
                    .setName("Test3")
                    .setLogin("Test3")
                    .setPassword("Test3")
                    .setRole(roleOpt.orElse(role))
                    .build();
            List<User> usersToCheck = new ArrayList<>();
            usersToCheck.add(user);
            usersToCheck.add(user2);
            usersToCheck.add(user3);
            userService.save(user);
            userService.save(user2);
            userService.save(user3);
            List<User> users = userService.findAll();
            assertTrue(users.containsAll(usersToCheck));
            userService.delete(user);
            userService.delete(user2);
            userService.delete(user3);
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

            User user2 = new User.UserBuilder()
                    .setName("Test2")
                    .setLogin("Test2")
                    .setPassword("Test2")
                    .setRole(roleOpt.orElse(role))
                    .build();
            User user3 = new User.UserBuilder()
                    .setName("Test3")
                    .setLogin("Test3")
                    .setPassword("Test3")
                    .setRole(roleOpt.orElse(role))
                    .build();
            List<User> usersToCheck = new ArrayList<>();
            usersToCheck.add(user);
            usersToCheck.add(user2);
            usersToCheck.add(user3);
            userService.save(user);
            userService.save(user2);
            //userService.save(user3);
            List<User> users = userService.findAll();
            assertFalse(users.containsAll(usersToCheck));
            userService.delete(user);
            userService.delete(user2);
            userService.delete(user3);
        } catch (Exception e) {
            fail(e.getMessage());
            userService.delete(user);
        }
    }

    @Test
    public void loadUserByUsernameTest() {
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
                    .setPassword("12345")
                    .setRole(roleOpt.orElse(role))
                    .build();
            if (!roleOpt.isPresent()) {
                roleService.save(role);
            }
            userService.save(user);

            UserDetails userDetails = userService.loadUserByUsername("Test");
            assertEquals(userDetails.getPassword(),user.getPassword());
            assertTrue(userDetails.getAuthorities().contains(
                    new SimpleGrantedAuthority(user.getRole().getRoleEnum().toString())
            ));
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            userService.delete(user);
        }
    }

}
