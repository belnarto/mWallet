package com.vironit.mWallet.models;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.services.CurrencyService;
import com.vironit.mWallet.services.RoleService;
import com.vironit.mWallet.services.UserService;
import com.vironit.mWallet.services.WalletService;
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
public class RoleTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private WalletService walletService;

    private Role role;

    @Before
    public void setUp() {
        Wallet wallet;
        User user;
        Currency currency;

        Optional<User> userOpt = userService.findAll().stream()
                .filter(u -> u.getLogin().equals("Test"))
                .findAny();
        userOpt.ifPresent(userService::delete);

        role = new Role(RoleEnum.TST);
        Optional<Role> roleOpt = roleService.findAll().stream()
                .filter(r -> r.getRoleEnum().toString().equals("TST"))
                .findAny();
        if (!roleOpt.isPresent()) {
            roleService.save(role);
        }

        user = User.builder()
                .login("Test")
                .name("Test")
                .password("Test")
                .role(roleOpt.orElse(role))
                .updatedAt(LocalDateTime.now())
                .build();
        userService.save(user);

        currency = new Currency("TST", 0.01);
        Optional<Currency> currencyOpt = currencyService.findAll().stream()
                .filter(c -> c.getName().equals("TST"))
                .findAny();
        currencyOpt.ifPresent(c -> currencyService.delete(c));
        currencyService.save(currency);

        wallet = Wallet.builder()
                .user(user)
                .currency(currency)
                .build();
        walletService.save(wallet);
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

        currencyService.delete(currencyService.findByName("TST"));
    }

    @Test
    public void getId() {
        try {
            assertTrue(role.getId() >= 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getRoleEnum() {
        try {
            assertEquals(role.getRoleEnum(), RoleEnum.TST);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setId() {
        try {
            role.setId(0);
            assertEquals(role.getId(), 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            role.setId(-1);
            roleService.update(role);
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void setRoleEnum() {
        try {
            role.setRoleEnum(null);
            roleService.update(role);
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void equals1() {
        try {
            assertEquals(role, role);
            Role role2 = new Role(RoleEnum.DEFAULT);
            assertNotEquals(role, role2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void hashCode1() {
        try {
            assertEquals(role.hashCode(), role.hashCode());
            Role role2 = new Role(RoleEnum.DEFAULT);
            assertNotEquals(role.hashCode(), role2.hashCode());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void toString1() {
        try {
            assertTrue(role.toString().matches("Role\\(id=\\d+, roleEnum=TST\\)"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}