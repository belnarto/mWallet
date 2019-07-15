package com.vironit.mwallet.dao;

import com.vironit.mwallet.config.WebConfig;
import com.vironit.mwallet.models.Role;
import com.vironit.mwallet.models.RoleEnum;
import com.vironit.mwallet.models.User;
import com.vironit.mwallet.services.UserService;
import com.vironit.mwallet.services.exception.LoginAlreadyDefinedException;
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
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

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

        Optional<Role> roleOpt = roleDao.findAll().stream()
                .filter(r -> r.getRoleEnum().toString().equals("TST"))
                .findAny();
        roleOpt.ifPresent(roleDao::delete);

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

        Optional<Role> roleOpt = roleDao.findAll().stream()
                .filter(r -> r.getRoleEnum().toString().equals("TST2"))
                .findAny();
        roleOpt.ifPresent(roleDao::delete);
    }

    @Test
    public void save() throws LoginAlreadyDefinedException {
        roleDao.save(role);
        userService.save(user);
        assertEquals(userService.findById(user.getId()).getRole(), role);
    }

    @Test
    public void update() throws LoginAlreadyDefinedException {
        roleDao.save(role);
        userService.save(user);
        assertEquals(userService.findById(user.getId()).getRole(), role);
        role.setRoleEnum(RoleEnum.TST2);
        roleDao.update(role);
        assertEquals(userService.findById(user.getId()).getRole().getRoleEnum(), RoleEnum.TST2);
    }

    @Test
    public void delete() throws LoginAlreadyDefinedException {
        roleDao.save(role);
        userService.save(user);
        try {
            roleDao.delete(role);
            fail();
        } catch (Exception e) {
        }
        userService.delete(user);
        roleDao.delete(role);
    }

    @Test
    public void findById() {
        roleDao.save(role);
        assertEquals(roleDao.findById(role.getId()), role);
    }

    @Test
    public void findAll() {
        roleDao.save(role);
        assertTrue(roleDao.findAll().contains(role));
        roleDao.delete(role);
        assertFalse(roleDao.findAll().contains(role));
    }

    @Test
    public void findByName() {
        roleDao.save(role);
        assertEquals(roleDao.findByName("TST"),role);
    }
}