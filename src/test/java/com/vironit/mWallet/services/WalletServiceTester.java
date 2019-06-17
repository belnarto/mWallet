package com.vironit.mWallet.services;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.models.*;

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
public class WalletServiceTester {

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private RoleService roleService;

    private User user;
    private Wallet wallet;
    private Currency currency;
    private Role role;

    @Test
    public void constructorTest() {
        try {
            new WalletService();
        } catch (Exception e) {
            fail(e.getMessage());
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
            userService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            walletService.save(wallet);

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
//            userService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            walletService.save(wallet);

            fail();
        } catch (Exception e) {
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
            //CurrencyService.save(com.vironit.mWallet.currency);

            wallet = new Wallet(user, currency);
            walletService.save(wallet);

            fail();
        } catch (Exception e) {
            walletService.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        }

        try {
            wallet = new Wallet();
            walletService.save(wallet);
            fail();
        } catch (Exception e) {
            walletService.delete(wallet);
            currencyService.delete(currency);
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
            userService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            Currency currency2 = new Currency("TST2", 0.02);
            Optional<Currency> currencyOpt2 = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST2"))
                    .findAny();
            currencyOpt2.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency2);

            wallet = new Wallet(user, currency);
            walletService.save(wallet);

            wallet.setCurrency(currency2);
            walletService.update(wallet);

            assertTrue(walletService.findAll()
                    .stream()
                    .filter(w -> w.equals(wallet))
                    .findAny()
                    .map(w -> w.getCurrency().getName().equals("TST2"))
                    .orElse(false));

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
            userService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            walletService.save(wallet);

            walletService.delete(wallet);

            assertFalse(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.equals(wallet)));

            currencyService.delete(currency);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
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

            userService.delete(user);

            assertFalse(walletService.findAll()
                    .stream()
                    .anyMatch(w -> w.equals(wallet)));

            assertTrue(currencyService.findAll()
                    .stream()
                    .anyMatch(c -> c.equals(currency)));

            currencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
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

            currencyService.delete(currency);
            fail();


        } catch (Exception e) {
            userService.delete(user);
            currencyService.delete(currency);
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
            userService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            walletService.save(wallet);
            Wallet wallet2 = new Wallet(user, currency);
            walletService.save(wallet2);

            List<Wallet> wallets = walletService.findAll()
                    .stream()
                    .filter(w -> w.getId() == wallet.getId() || w.getId() == wallet2.getId())
                    .collect(Collectors.toList());

            assertEquals(wallets.size(), 2);

            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
            userService.delete(user);
        }
    }

    @Test
    public void findAllByUserTest() {

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
            wallet.setBalance(0.2);
            walletService.save(wallet);

            Currency currency2 = new Currency("TST2", 0.02);
            currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST2"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency2);

            Wallet wallet2 = new Wallet(user, currency2);
            wallet2.setBalance(0.1);
            walletService.save(wallet2);

            List<Wallet> wallets = walletService.findAllByUser(user);

            assertEquals(wallets.size(), 2);

            walletService.delete(wallet);
            walletService.delete(wallet2);
            userService.delete(user);
            currencyService.delete(currency);
            currencyService.delete(currency2);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
            userService.delete(user);
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
            userService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            walletService.save(wallet);
            Wallet wallet2 = new Wallet(user, currency);
            walletService.save(wallet2);

            List<Wallet> wallets = walletService.findAll()
                    .stream()
                    .filter(w -> w.equals(wallet) || w.equals(wallet2))
                    .collect(Collectors.toList());

            assertEquals(wallets.size(), 2);

            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
            userService.delete(user);
        }
    }
}
