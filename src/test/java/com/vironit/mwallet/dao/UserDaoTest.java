package com.vironit.mwallet.dao;

import com.vironit.mwallet.config.WebConfig;
import com.vironit.mwallet.models.*;
import com.vironit.mwallet.services.RoleService;
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
public class UserDaoTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserDao userDao;

    private User user;

    @Before
    public void setUp() throws InterruptedException {
        Role role;

        Optional<User> userOpt = userDao.findAll().stream()
                .filter(u -> u.getLogin().equals("Test"))
                .findAny();
        userOpt.ifPresent(userDao::delete);

        Optional<Role> roleOpt = roleService.findAll().stream()
                .filter(r -> r.getRoleEnum().toString().equals("TST"))
                .findAny();
        roleOpt.ifPresent(roleService::delete);

        role = new Role(RoleEnum.TST);
        roleService.save(role);
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
        Optional<User> userOpt = userDao.findAll().stream()
                .filter(u -> u.getLogin().equals("Test"))
                .findAny();
        userOpt.ifPresent(userDao::delete);

        userOpt = userDao.findAll().stream()
                .filter(u -> u.getLogin().equals("Test2"))
                .findAny();
        userOpt.ifPresent(userDao::delete);

        Optional<Role> roleOpt = roleService.findAll().stream()
                .filter(r -> r.getRoleEnum().toString().equals("TST"))
                .findAny();
        roleOpt.ifPresent(roleService::delete);
    }

    @Test
    public void save() {
        userDao.save(user);
        assertEquals(userDao.findById(user.getId()), user);
    }

    @Test
    public void update() {
        userDao.save(user);
        user.setLogin("Test2");
        userDao.update(user);
        assertEquals(userDao.findById(user.getId()).getLogin(), "Test2");
    }

    @Test
    public void delete() {
        userDao.save(user);
        userDao.delete(user);
        assertNull(userDao.findById(user.getId()));
    }

    @Test
    public void findById() {
        userDao.save(user);
        assertEquals(userDao.findById(user.getId()), user);
        assertNull(userDao.findById(0));
    }

    @Test
    public void findByLogin() {
        userDao.save(user);
        assertEquals(userDao.findByLogin(user.getLogin()), user);
        assertNull(userDao.findByLogin(""));
    }

    @Test
    public void findAll() {
        userDao.save(user);
        assertTrue(userDao.findAll().contains(user));
    }
}