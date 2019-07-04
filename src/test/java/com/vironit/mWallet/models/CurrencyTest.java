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
public class CurrencyTest {


    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private WalletService walletService;

    private Currency currency;

    @Before
    public void setUp() throws InterruptedException {
        Wallet wallet;
        Role role;
        User user;

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
        currencyService.save(currency);

        wallet = Wallet.builder()
                .user(user)
                .currency(currency)
                .build();
        walletService.save(wallet);

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

        Optional<Currency> currencyOpt = currencyService.findAll().stream()
                .filter(c -> c.getName().equals("TST"))
                .findAny();
        currencyOpt.ifPresent(c -> currencyService.delete(c));

        currencyOpt = currencyService.findAll().stream()
                .filter(c -> c.getName().equals("TST2"))
                .findAny();
        currencyOpt.ifPresent(c -> currencyService.delete(c));

        Optional<Role> roleOpt = roleService.findAll().stream()
                .filter(r -> r.getRoleEnum().toString().equals("TST"))
                .findAny();
        roleOpt.ifPresent(roleService::delete);
    }

    @Test
    public void getId() {
        try {
            assertTrue(currency.getId() >= 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getName() {
        try {
            assertEquals(currency.getName(), "TST");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getRate() {
        try {
            assertEquals(currency.getRate(), 0.01, 0.0000001);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setId() {
        try {
            currency.setId(0);
            assertEquals(currency.getId(), 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            currency.setId(-1);
            currencyService.update(currency);
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void setName() {
        try {
            currency.setName("TST2");
            assertEquals(currency.getName(), "TST2");
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            currency.setName("");
            currencyService.update(currency);
            fail();
        } catch (Exception e) {
        }

        try {
            currency.setName("12");
            currencyService.update(currency);
            fail();
        } catch (Exception e) {
        }

        try {
            currency.setName("1234567");
            currencyService.update(currency);
            fail();
        } catch (Exception e) {
        }

        try {
            currency.setName(null);
            currencyService.update(currency);
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void setRate() {
        try {
            currency.setRate(0.02);
            assertEquals(currency.getRate(), 0.02, 0.0000001);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            currency.setRate(0);
            currencyService.update(currency);
            fail();
        } catch (Exception e) {
        }

        try {
            currency.setRate(-0.01);
            currencyService.update(currency);
            fail();
        } catch (Exception e) {
        }

    }

    @Test
    public void equals1() {
        try {
            assertEquals(currency, currency);
            Currency currency2 = new Currency("TST2", 0.01);
            assertNotEquals(currency, currency2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void hashCode1() {
        try {
            assertEquals(currency.hashCode(), currency.hashCode());
            Currency currency2 = new Currency("TST2", 0.01);
            assertNotEquals(currency.hashCode(), currency2.hashCode());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void toString1() {
        try {
            assertTrue(currency.toString().matches("Currency\\(id=\\d+, name=TST, rate=\\d+.\\d+\\)"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}