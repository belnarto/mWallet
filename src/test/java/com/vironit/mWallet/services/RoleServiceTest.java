package com.vironit.mWallet.services;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.models.Role;
import com.vironit.mWallet.models.RoleEnum;
import com.vironit.mWallet.models.User;
import com.vironit.mWallet.services.exception.LoginAlreadyDefinedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    private Role role;
    private User user;

    @Before
    public void setUp() throws InterruptedException {

        Optional<User> userOpt = userService.findAll().stream()
                .filter(u -> u.getLogin().equals("Test"))
                .findAny();
        userOpt.ifPresent(userService::delete);

        Optional<Role> roleOpt = roleService.findAll().stream()
                .filter(r -> r.getRoleEnum().toString().equals("TST"))
                .findAny();
        roleOpt.ifPresent(roleService::delete);

        role = new Role(RoleEnum.TST);

        user = User.builder()
                .login("Test")
                .name("Test")
                .password("Test")
                .role(role)
                .updatedAt(LocalDateTime.now())
                .build();


        Thread.sleep(1);
    }

    @After
    public void tearDown() {
        Optional<User> userOpt = userService.findAll().stream()
                .filter(u -> u.getLogin().equals("Test"))
                .findAny();
        userOpt.ifPresent(userService::delete);

        userOpt = userService.findAll().stream()
                .filter(u -> u.getLogin().equals("Test2"))
                .findAny();
        userOpt.ifPresent(userService::delete);

        Optional<Role> roleOpt = roleService.findAll().stream()
                .filter(r -> r.getRoleEnum().toString().equals("TST2"))
                .findAny();
        roleOpt.ifPresent(roleService::delete);
    }

    @Test
    public void findById() {
        roleService.save(role);
        assertEquals(roleService.findById(role.getId()), role);

    }

    @Test
    public void findByName() {
        roleService.save(role);
        assertEquals(roleService.findByName("TST"), role);

    }

    @Test
    public void save() throws LoginAlreadyDefinedException {
        roleService.save(role);
        userService.save(user);
        assertEquals(userService.findById(user.getId()).getRole(), role);
    }

    @Test
    public void delete() throws LoginAlreadyDefinedException {
        roleService.save(role);
        userService.save(user);
        try {
            roleService.delete(role);
            fail();
        } catch (Exception e) {
        }
        userService.delete(user);
        roleService.delete(role);
    }

    @Test
    public void update() throws LoginAlreadyDefinedException {
        roleService.save(role);
        userService.save(user);
        assertEquals(userService.findById(user.getId()).getRole(), role);
        role.setRoleEnum(RoleEnum.TST2);
        roleService.update(role);
        assertEquals(userService.findById(user.getId()).getRole().getRoleEnum(), RoleEnum.TST2);
    }

    @Test
    public void findAll() {
        roleService.save(role);
        assertTrue(roleService.findAll().contains(role));
        roleService.delete(role);
        assertFalse(roleService.findAll().contains(role));
    }
}