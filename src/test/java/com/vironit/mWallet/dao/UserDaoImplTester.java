package com.vironit.mWallet.dao;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.dao.impl.UserDaoImpl;
import com.vironit.mWallet.models.Role;
import com.vironit.mWallet.models.RoleEnum;
import com.vironit.mWallet.services.RoleService;
import com.vironit.mWallet.models.User;
import com.vironit.mWallet.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class UserDaoImplTester {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserDao userDao;

    private User user;
    private Role role;

    @Test
    public void constructorTest() {
        try {
            new UserDaoImpl();
        } catch (Exception e) {
            fail(e.getMessage());
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

            userDao.save(user);
            userDao.save(user2);
            userDao.save(user3);

            List<User> users = userDao.findAll();

            assertTrue(users.containsAll(usersToCheck));

            userDao.delete(user);
            userDao.delete(user2);
            userDao.delete(user3);
        } catch (Exception e) {
            fail(e.getMessage());
            userDao.delete(user);
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
            userDao.save(user);

            List<User> users = userDao.findAll().stream()
                    .filter(u -> u.getName().equals("Test"))
                    .collect(Collectors.toList());

            assertTrue(users.contains(user));

            userDao.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            userDao.delete(user);
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
            userDao.save(user);

            assertEquals(userDao.findById(user.getId()), user);

            userDao.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            userDao.delete(user);
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
            userDao.save(user);

            assertEquals(userDao.findById(user.getId()).getName(), "Test");

            user.setName("Test2");
            userDao.update(user);

            assertEquals(userDao.findById(user.getId()).getName(), "Test2");

            userDao.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            userDao.delete(user);
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
            userDao.save(user);

            assertNotNull(userDao.findById(user.getId()));

            userDao.delete(user);

            assertNull(userDao.findById(user.getId()));
        } catch (Exception e) {
            fail(e.getMessage());
            userDao.delete(user);
        }
    }
}
