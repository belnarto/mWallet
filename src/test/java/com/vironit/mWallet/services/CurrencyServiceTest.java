package com.vironit.mWallet.services;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.models.Currency;
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
public class CurrencyServiceTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyService currencyService;

    private Currency currency;

    @Before
    public void setUp() throws InterruptedException, LoginAlreadyDefinedException {
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
        Optional<Currency> currencyOpt = currencyService.findAll().stream()
                .filter(c -> c.getName().equals("TST"))
                .findAny();
        currencyOpt.ifPresent(c -> currencyService.delete(c));


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

        Optional<Currency> currencyOpt = currencyService.findAll().stream()
                .filter(c -> c.getName().equals("TST"))
                .findAny();
        currencyOpt.ifPresent(c -> currencyService.delete(c));

        currencyOpt = currencyService.findAll().stream()
                .filter(c -> c.getName().equals("TST2"))
                .findAny();
        currencyOpt.ifPresent(c -> currencyService.delete(c));
    }

    @Test
    public void findById() {
        currencyService.save(currency);
        assertEquals(currencyService.findById(currency.getId()), currency);
        assertNull(currencyService.findById(0));
    }

    @Test
    public void findByName() {
        assertNull(currencyService.findByName("TST"));
        currencyService.save(currency);
        assertEquals(currencyService.findByName("TST"), currency);
    }

    @Test
    public void findAll() {
        currencyService.save(currency);
        assertTrue(currencyService.findAll().contains(currency));
    }

    @Test
    public void save() {
        currencyService.save(currency);
        assertEquals(currencyService.findById(currency.getId()), currency);
    }

    @Test
    public void delete() {
        currencyService.save(currency);
        currencyService.delete(currency);
        assertNull(currencyService.findById(currency.getId()));
    }

    @Test
    public void update() {
        currencyService.save(currency);
        assertEquals(currencyService.findById(currency.getId()).getName(), "TST");
        currency.setName("TST2");
        currencyService.update(currency);
        assertEquals(currencyService.findById(currency.getId()).getName(), "TST2");
    }
}