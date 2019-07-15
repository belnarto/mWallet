package com.vironit.mwallet.services;

import com.vironit.mwallet.config.WebConfig;
import com.vironit.mwallet.exceptions.WalletStatusException;
import com.vironit.mwallet.models.*;
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
public class WalletServiceTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private WalletService walletService;

    private User user;
    private Wallet wallet;

    @Before
    public void setUp() throws InterruptedException, LoginAlreadyDefinedException {
        Role role;
        Currency currency;

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

        currencyOpt = currencyService.findAll().stream()
                .filter(c -> c.getName().equals("TST2"))
                .findAny();
        currencyOpt.ifPresent(c -> currencyService.delete(c));
        currencyService.save(currency);

        wallet = Wallet.builder()
                .user(user)
                .currency(currency)
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
        walletService.save(wallet);
        assertEquals(walletService.findById(wallet.getId()), wallet);
        assertNull(walletService.findById(0));
    }

    @Test
    public void findAllByUser() {
        assertTrue(walletService.findAllByUser(user).isEmpty());
        walletService.save(wallet);
        assertTrue(walletService.findAllByUser(user).stream().allMatch(w -> w.getUser().equals(user)));
    }

    @Test
    public void findAll() {
        walletService.save(wallet);
        assertTrue(walletService.findAll().contains(wallet));
    }

    @Test
    public void save() {
        walletService.save(wallet);
        assertEquals(walletService.findById(wallet.getId()), wallet);
    }

    @Test
    public void delete() {
        walletService.save(wallet);
        walletService.delete(wallet);
        assertNull(walletService.findById(wallet.getId()));
    }

    @Test
    public void update() {
        walletService.save(wallet);
        assertEquals(walletService.findById(wallet.getId()).getStatus(), WalletStatusEnum.ACTIVE);
        wallet.setStatus(WalletStatusEnum.BLOCKED);
        walletService.update(wallet);
        assertEquals(walletService.findById(wallet.getId()).getStatus(), WalletStatusEnum.BLOCKED);
    }

    @Test
    public void addBalance() {
        walletService.save(wallet);
        assertEquals(walletService.findById(wallet.getId()).getBalance(), 0.0, 0.000001);
        walletService.addBalance(wallet, 1);
        assertEquals(walletService.findById(wallet.getId()).getBalance(), 1.0, 0.000001);

        try {
            walletService.addBalance(wallet, 0);
            fail();
        } catch (Exception ignored) {
        }

        try {
            walletService.addBalance(wallet, -1);
            fail();
        } catch (Exception ignored) {
        }

        walletService.blockWallet(wallet);
        try {
            walletService.addBalance(wallet, 1);
            fail();
        } catch (WalletStatusException ignored) {
        }

        walletService.activateWallet(wallet);
        try {
            walletService.addBalance(wallet, 1);
            assertEquals(walletService.findById(wallet.getId()).getBalance(), 2.0, 0.000001);
        } catch (WalletStatusException ignored) {
            fail();
        }
    }

    @Test
    public void reduceBalance() {
        walletService.save(wallet);
        assertEquals(walletService.findById(wallet.getId()).getBalance(), 0.0, 0.000001);
        walletService.addBalance(wallet, 1);
        assertEquals(walletService.findById(wallet.getId()).getBalance(), 1.0, 0.000001);

        walletService.reduceBalance(wallet, 0.5);
        assertEquals(walletService.findById(wallet.getId()).getBalance(), 0.5, 0.000001);


        try {
            walletService.reduceBalance(wallet, 0);
            fail();
        } catch (Exception ignored) {
        }

        try {
            walletService.reduceBalance(wallet, -1);
            fail();
        } catch (Exception ignored) {
        }

        walletService.blockWallet(wallet);
        try {
            walletService.reduceBalance(wallet, 0.1);
            fail();
        } catch (WalletStatusException ignored) {
        }

        walletService.activateWallet(wallet);
        try {
            walletService.reduceBalance(wallet, 0.1);
            assertEquals(walletService.findById(wallet.getId()).getBalance(), 0.4, 0.000001);
        } catch (WalletStatusException ignored) {
            fail();
        }
    }

    @Test
    public void transferMoney() {
        walletService.save(wallet);

        Currency currency2 = new Currency("TST2", 0.02);
        currencyService.save(currency2);
        Wallet wallet2 = Wallet.builder()
                .currency(currency2)
                .user(user)
                .build();
        walletService.save(wallet2);

        assertEquals(walletService.findById(wallet.getId()).getBalance(), 0.0, 0.000001);
        assertEquals(walletService.findById(wallet2.getId()).getBalance(), 0.0, 0.000001);
        walletService.addBalance(wallet, 1);
        assertEquals(walletService.findById(wallet.getId()).getBalance(), 1.0, 0.000001);
        walletService.transferMoney(wallet, wallet2, 0.5);
        assertEquals(walletService.findById(wallet.getId()).getBalance(), 0.5, 0.000001);
        assertEquals(walletService.findById(wallet2.getId()).getBalance(), 0.25, 0.000001);

        try {
            walletService.transferMoney(wallet, wallet2, 0);
            fail();
        } catch (Exception ignored) {
        }

        try {
            walletService.transferMoney(wallet, wallet2, -1);
            fail();
        } catch (Exception ignored) {
        }

        try {
            walletService.blockWallet(wallet);
            walletService.transferMoney(wallet, wallet2, 0.5);
            fail();
        } catch (WalletStatusException ignored) {
            assertEquals(walletService.findById(wallet.getId()).getBalance(), 0.5, 0.000001);
            assertEquals(walletService.findById(wallet2.getId()).getBalance(), 0.25, 0.000001);
        }

        try {
            walletService.activateWallet(wallet);
            walletService.blockWallet(wallet2);
            walletService.transferMoney(wallet, wallet2, 0.5);
            fail();
        } catch (WalletStatusException ignored) {
            assertEquals(walletService.findById(wallet.getId()).getBalance(), 0.5, 0.000001);
            assertEquals(walletService.findById(wallet2.getId()).getBalance(), 0.25, 0.000001);
        }
    }

    @Test
    public void blockWallet() {
        walletService.save(wallet);
        walletService.blockWallet(wallet);
        assertEquals(walletService.findById(wallet.getId()).getStatus(), WalletStatusEnum.BLOCKED);
    }

    @Test
    public void activateWallet() {
        walletService.save(wallet);
        walletService.blockWallet(wallet);
        assertEquals(walletService.findById(wallet.getId()).getStatus(), WalletStatusEnum.BLOCKED);
        walletService.activateWallet(wallet);
        assertEquals(walletService.findById(wallet.getId()).getStatus(), WalletStatusEnum.ACTIVE);
    }
}