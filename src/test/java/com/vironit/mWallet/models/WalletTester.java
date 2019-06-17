package com.vironit.mWallet.models;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.services.RoleService;
import com.vironit.mWallet.services.CurrencyService;
import com.vironit.mWallet.services.UserService;
import com.vironit.mWallet.services.WalletService;

import java.util.Optional;

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
public class WalletTester {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private RoleService roleService;

    private User user;
    private int id;
    private Currency currency;
    private Wallet wallet;
    private Role role;

    @Test
    public void constructorTest() {
        try {
            new Wallet();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            role = new Role(RoleEnum.TST);
            user = new User.UserBuilder()
                    .setName("Test")
                    .setLogin("Test")
                    .setPassword("Test")
                    .setRole(role)
                    .build();
            currency = new Currency("TST", 0.01);
            wallet = new Wallet(user, currency);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        }
    }

    @Test
    public void getIdTest() {
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

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            walletService.save(wallet);
            id = wallet.getId();

            assertTrue(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.getId() == id));

            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        }
    }

    @Test
    public void setAndGetUserTest() {
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

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            walletService.save(wallet);

            assertTrue(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.getUser().equals(user)));

            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
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
            userService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            walletService.save(wallet);
            id = wallet.getId();

            assertTrue(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.getUser().equals(user)));

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
            userService.save(user2);

            wallet.setUser(user2);
            walletService.update(wallet);

            assertTrue(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.getUser().equals(user2) && w.getId() == id));

            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
            userService.delete(user2);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        }
    }

    @Test
    public void setAndGetCurrencyTest() {
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

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            walletService.save(wallet);

            assertTrue(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.getCurrency().equals(currency)));

            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
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
            userService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            walletService.save(wallet);
            id = wallet.getId();

            assertTrue(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.getUser().equals(user)));

            Currency currency2 = new Currency("TST2", 0.02);
            currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST2"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency2);

            wallet.setCurrency(currency2);
            walletService.update(wallet);

            assertTrue(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.getCurrency().equals(currency2) && w.getId() == id));

            walletService.delete(wallet);
            currencyService.delete(currency);
            currencyService.delete(currency2);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        }
    }

    @Test
    public void setAndGetBalanceTest() {
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

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            wallet.setBalance(0.03);
            walletService.save(wallet);
            id = wallet.getId();

            assertTrue(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.getId() == id && Math.abs(w.getBalance() - 0.03) < 0.000001));

            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
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
            userService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            wallet.setBalance(0.03);
            walletService.save(wallet);
            id = wallet.getId();

            assertTrue(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.getId() == id && Math.abs(w.getBalance() - 0.03) < 0.000001));

            wallet.setBalance(wallet.getBalance() + 0.02);
            walletService.update(wallet);

            assertTrue(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.getId() == id && Math.abs(w.getBalance() - 0.05) < 0.000001));

            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        }
    }

    @Test
    public void equalsTest() {
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

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            wallet.setBalance(0.03);
            walletService.save(wallet);
            id = wallet.getId();

            assertTrue(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.getId() == id && w.equals(wallet)));

            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
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
            userService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            wallet.setBalance(0.03);
            walletService.save(wallet);
            id = wallet.getId();

            Wallet wallet2 = new Wallet(user, currency);
            wallet.setBalance(0.03);
            walletService.save(wallet2);

            assertFalse(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.getId() == id && w.equals(wallet2)));

            walletService.delete(wallet);
            walletService.delete(wallet2);
            currencyService.delete(currency);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        }
    }

    @Test
    public void addBalanceTest() {
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

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            walletService.save(wallet);
            id = wallet.getId();

            walletService.addBalance(wallet, 0.3);

            assertTrue(walletService.findAllByUser(user)
                    .stream()
                    .anyMatch(w -> w.getId() == id && Math.abs(w.getBalance() - 0.3) < 0.000001));

            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        }
    }

    @Test
    public void reduceBalanceTest() {
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

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            walletService.save(wallet);
            id = wallet.getId();

            walletService.addBalance(wallet, 0.3);
            walletService.reduceBalance(wallet, 0.2);

            assertTrue(walletService.findAllByUser(user)
                    .stream()
                    .anyMatch(w -> w.getId() == id && Math.abs(w.getBalance() - 0.1) < 0.000001));

            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
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
            userService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            walletService.save(wallet);
            id = wallet.getId();

            walletService.addBalance(wallet, 0.3);
            walletService.reduceBalance(wallet, 0.4);

            fail();
        } catch (Exception e) {
            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        }
    }

    @Test
    public void transferMoneyTest() {
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

            currency = new Currency("TST", 0.01);
            walletService.findAll()
                    .stream()
                    .filter(w -> w.getCurrency().getName().contains("TST"))
                    .forEach(walletService::delete);

            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            wallet.setBalance(0.031);
            walletService.save(wallet);
            id = wallet.getId();

            Wallet wallet2 = new Wallet(user, currency);
            wallet2.setBalance(0.02);
            walletService.save(wallet2);
            int id2 = wallet2.getId();

            walletService.transferMoney(wallet, wallet2, 0.03);

            assertTrue(walletService.findAllByUser(user)
                    .stream()
                    .anyMatch(w -> (w.getId() == id && Math.abs(w.getBalance() - 0.001) < 0.01)));

            assertTrue(walletService.findAllByUser(user)
                    .stream()
                    .anyMatch(w -> (w.getId() == id2 && Math.abs(w.getBalance() - 0.05) < 0.01)));

            walletService.delete(wallet);
            walletService.delete(wallet2);
            currencyService.delete(currency);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
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
            userService.save(user);

            currency = new Currency("TST", 0.01);
            walletService.findAll()
                    .stream()
                    .filter(w -> w.getCurrency().getName().contains("TST"))
                    .forEach(walletService::delete);

            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            wallet.setBalance(0.031);
            walletService.save(wallet);
            id = wallet.getId();

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
            userService.save(user2);

            Currency currency2 = new Currency("TST2", 0.02);
            currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST2"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency2);

            Wallet wallet2 = new Wallet(user2, currency2);
            wallet2.setBalance(0.02);
            walletService.save(wallet2);
            int id2 = wallet2.getId();

            walletService.transferMoney(wallet, wallet2, 0.03);

            assertTrue(walletService.findAllByUser(user)
                    .stream()
                    .anyMatch(w -> (w.getId() == id && Math.abs(w.getBalance() - 0.001) < 0.01)));

            assertTrue(walletService.findAllByUser(user2)
                    .stream()
                    .anyMatch(w -> (w.getId() == id2 && Math.abs(w.getBalance() - 0.035) < 0.01)));

            walletService.delete(wallet);
            walletService.delete(wallet2);
            currencyService.delete(currency);
            currencyService.delete(currency2);
            userService.delete(user);
            userService.delete(user2);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
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
            userService.save(user);

            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("BYN"))
                    .findAny();

            wallet = new Wallet(user, currencyOpt.orElse(null));
            wallet.setBalance(0.031);
            walletService.save(wallet);
            id = wallet.getId();

            Wallet wallet2 = new Wallet(user, currencyOpt.orElse(null));
            wallet2.setBalance(0.02);
            walletService.save(wallet2);
            int id2 = wallet2.getId();

            walletService.transferMoney(wallet, wallet2, 0.03);

            assertTrue(walletService.findAllByUser(user)
                    .stream()
                    .anyMatch(w -> (w.getId() == id && Math.abs(w.getBalance() - 0.001) < 0.01)));

            assertTrue(walletService.findAllByUser(user)
                    .stream()
                    .anyMatch(w -> (w.getId() == id2 && Math.abs(w.getBalance() - 0.05) < 0.01)));

            walletService.delete(wallet);
            walletService.delete(wallet2);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        }
    }

    @Test
    public void statusTest() {
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

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            wallet.setBalance(0.03);
            walletService.save(wallet);
            id = wallet.getId();

            assertTrue(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.getId() == id && w.getStatus().equals(WalletStatusEnum.ACTIVE)));

            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
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
            userService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            wallet.setBalance(0.03);
            walletService.save(wallet);
            id = wallet.getId();
            wallet.blockWallet();
            walletService.update(wallet);

            assertTrue(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.getId() == id && w.getStatus().equals(WalletStatusEnum.BLOCKED)));

            wallet.activateWallet();
            walletService.update(wallet);

            assertFalse(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.getId() == id && w.getStatus().equals(WalletStatusEnum.BLOCKED)));

            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        }
    }
}
