package com.vironit.mwallet.dao;

import com.vironit.mwallet.config.WebConfig;
import com.vironit.mwallet.models.*;
import com.vironit.mwallet.services.CurrencyService;
import com.vironit.mwallet.services.RoleService;
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
public class WalletDaoTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private WalletDao walletDao;

    private User user;
    private Currency currency;
    private Wallet wallet;

    @Before
    public void setUp() throws InterruptedException, LoginAlreadyDefinedException {
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
    }

    @Test
    public void findById() {
        walletDao.save(wallet);
        assertEquals(walletDao.findById(wallet.getId()), wallet);
        assertNull(walletDao.findById(0));
    }

    @Test
    public void save() {
        walletDao.save(wallet);
        assertEquals(walletDao.findById(wallet.getId()), wallet);
    }

    @Test
    public void update() {
        walletDao.save(wallet);
        assertEquals(walletDao.findById(wallet.getId()).getWalletStatus(), WalletStatusEnum.ACTIVE);
        wallet.setWalletStatus(WalletStatusEnum.BLOCKED);
        walletDao.update(wallet);
        assertEquals(walletDao.findById(wallet.getId()).getWalletStatus(), WalletStatusEnum.BLOCKED);
    }

    @Test
    public void delete() {
        walletDao.save(wallet);
        walletDao.delete(wallet);
        assertNull(walletDao.findById(wallet.getId()));
    }

    @Test
    public void findAllByUser() {
        assertTrue(walletDao.findAllByUser(user).isEmpty());
        walletDao.save(wallet);
        assertTrue(walletDao.findAllByUser(user).stream().allMatch(w -> w.getUser().equals(user)));
    }

    @Test
    public void findAllByCurrency() {
        assertTrue(walletDao.findAllByCurrency(currency).isEmpty());
        walletDao.save(wallet);
        assertTrue(walletDao.findAllByCurrency(currency).stream().allMatch(w -> w.getCurrency().equals(currency)));
    }

    @Test
    public void findAll() {
        walletDao.save(wallet);
        assertTrue(walletDao.findAll().contains(wallet));
    }
}