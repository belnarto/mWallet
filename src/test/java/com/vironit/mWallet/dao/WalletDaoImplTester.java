package com.vironit.mWallet.dao;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.dao.impl.WalletDaoImpl;
import com.vironit.mWallet.models.*;
import com.vironit.mWallet.services.RoleService;
import com.vironit.mWallet.services.CurrencyService;
import com.vironit.mWallet.services.UserService;

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
public class WalletDaoImplTester {

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private WalletDao walletDao;

    private Wallet wallet;
    private User user;
    private Currency currency;
    private Currency currency2;
    private Role role;

    @SuppressWarnings("WeakerAccess")
    @Test
    public void constructorTest() {
        try {
            new WalletDaoImpl();
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
            walletDao.save(wallet);

            walletDao.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletDao.delete(wallet);
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
            //userService.save(user);


            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            walletDao.save(wallet);

            fail();
        } catch (Exception e) {
            currencyService.delete(currency);
            walletDao.delete(wallet);
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
            walletDao.save(wallet);

            fail();
        } catch (Exception e) {
            walletDao.delete(wallet);
            currencyService.delete(currency);
            userService.delete(user);
        }

        try {
            wallet = new Wallet();
            walletDao.save(wallet);
            fail();
        } catch (Exception e) {
            walletDao.delete(wallet);
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

            currency2 = new Currency("TST2", 0.02);
            Optional<Currency> currencyOpt2 = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST2"))
                    .findAny();
            currencyOpt2.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency2);

            wallet = new Wallet(user, currency);
            walletDao.save(wallet);

            wallet.setCurrency(currency2);
            walletDao.update(wallet);

            assertTrue(walletDao.findAll()
                    .stream()
                    .filter(w -> w.equals(wallet))
                    .findAny()
                    .map(w -> w.getCurrency().getName().equals("TST2"))
                    .orElse(false));

            walletDao.delete(wallet);
            currencyService.delete(currency);
            currencyService.delete(currency2);
            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            walletDao.delete(wallet);
            currencyService.delete(currency);
            currencyService.delete(currency2);
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
            walletDao.save(wallet);

            walletDao.delete(wallet);

            assertFalse(walletDao.findAll()
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
            walletDao.save(wallet);

            userService.delete(user);

            assertFalse(walletDao.findAll()
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
            walletDao.save(wallet);

            currencyService.delete(currency);

            fail();

        } catch (Exception e) {
            userService.delete(user);
            currencyService.delete(currency);
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
            walletDao.save(wallet);
            Wallet wallet2 = new Wallet(user, currency);
            walletDao.save(wallet2);

            List<Wallet> wallets = walletDao.findAll()
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
            walletDao.save(wallet);
            Wallet wallet2 = new Wallet(user, currency);
            walletDao.save(wallet2);

            List<Wallet> wallets = walletDao.findAll()
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

}
