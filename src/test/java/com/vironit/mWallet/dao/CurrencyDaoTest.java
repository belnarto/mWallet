package com.vironit.mWallet.dao;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.models.*;
import com.vironit.mWallet.services.RoleService;
import com.vironit.mWallet.services.UserService;
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
public class CurrencyDaoTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyDao currencyDao;

    private Currency currency;

    @Before
    public void setUp() throws InterruptedException {
        User user;
        Role role;

        Optional<User> userOpt = userService.findAll().stream()
                .filter(u -> u.getLogin().equals("Test"))
                .findAny();
        userOpt.ifPresent(userService::delete);

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
        userService.save(user);

        currency = new Currency("TST", 0.01);
        Optional<Currency> currencyOpt = currencyDao.findAll().stream()
                .filter(c -> c.getName().equals("TST"))
                .findAny();
        currencyOpt.ifPresent(c -> currencyDao.delete(c));


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
                .filter(r -> r.getRoleEnum().toString().equals("TST"))
                .findAny();
        roleOpt.ifPresent(roleService::delete);

        Optional<Currency> currencyOpt = currencyDao.findAll().stream()
                .filter(c -> c.getName().equals("TST"))
                .findAny();
        currencyOpt.ifPresent(c -> currencyDao.delete(c));

        currencyOpt = currencyDao.findAll().stream()
                .filter(c -> c.getName().equals("TST2"))
                .findAny();
        currencyOpt.ifPresent(c -> currencyDao.delete(c));
    }

    @Test
    public void save() {
        try {
            currency.setName("1234567");
            currencyDao.save(currency);
            fail();
        } catch (Exception e) {
        }

        currency.setName("TST");
        currencyDao.save(currency);
        assertEquals(currencyDao.findById(currency.getId()), currency);
    }

    @Test
    public void update() {
        currencyDao.save(currency);
        assertEquals(currencyDao.findById(currency.getId()).getName(), "TST");
        currency.setName("TST2");
        currencyDao.update(currency);
        assertEquals(currencyDao.findById(currency.getId()).getName(), "TST2");

        try {
            currency.setName("12");
            currencyDao.update(currency);
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void delete() {
        currencyDao.save(currency);
        try {
            currency.setName("12");
            currencyDao.delete(currency);
            fail();
        } catch (Exception e) {
        }
        currency.setName("TST");
        currencyDao.delete(currency);
        assertNull(currencyDao.findById(currency.getId()));
    }

    @Test
    public void findAll() {
        currencyDao.save(currency);
        assertTrue(currencyDao.findAll().contains(currency));
    }

    @Test
    public void findById() {
        currencyDao.save(currency);
        assertEquals(currencyDao.findById(currency.getId()), currency);
        assertNull(currencyDao.findById(0));
    }

    @Test
    public void findByName() {
        assertNull(currencyDao.findByName("TST"));
        currencyDao.save(currency);
        assertEquals(currencyDao.findByName("TST"), currency);
    }
}