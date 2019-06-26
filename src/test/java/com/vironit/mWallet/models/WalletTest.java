package com.vironit.mWallet.models;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.dao.UserDao;
import com.vironit.mWallet.services.CurrencyService;
import com.vironit.mWallet.services.RoleService;
import com.vironit.mWallet.services.UserService;
import com.vironit.mWallet.services.WalletService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class WalletTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Role role;
    private User user;
    private Currency currency;
    private Wallet wallet;

    @Before
    public void setUp() {

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
    public void builder() {
        try {
            Wallet wallet2 = Wallet.builder()
                    .user(user)
                    .currency(currency)
                    .build();

            assertEquals(wallet.getCurrency(), wallet2.getCurrency());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void toBuilder() {
        try {
            Wallet wallet2 = wallet.toBuilder()
                    .build();

            assertEquals(wallet, wallet2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getId() {
        try {
            assertTrue(wallet.getId() >= 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getUser() {
        try {
            assertEquals(wallet.getUser(), user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getCurrency() {
        try {
            assertEquals(wallet.getCurrency(), currency);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getBalance() {
        try {
            assertEquals(wallet.getBalance(), 0.0, 0.0000001);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getStatus() {
        try {
            assertEquals(wallet.getStatus(), WalletStatusEnum.ACTIVE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setId() {

        try {
            wallet.setId(0);
            assertEquals(wallet.getId(), 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            wallet.setId(-1);
            walletService.update(wallet);
            fail();
        } catch (Exception e) {
        }

    }

    @Test
    public void setUser() {

        try {
            User user2 = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            wallet.setUser(user2);
            assertEquals(wallet.getUser(), user2);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            wallet.setUser(null);
            walletService.update(wallet);
            fail();
        } catch (Exception e) {
        }

    }

    @Test
    public void setCurrency() {

        try {
            Currency currency2 = new Currency("TST2", 0.01);
            wallet.setCurrency(currency2);
            assertEquals(wallet.getCurrency(), currency2);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            wallet.setCurrency(null);
            walletService.update(wallet);
            fail();
        } catch (Exception e) {
        }

    }

    @Test
    public void setBalance() {

        try {
            wallet.setBalance(10);
            assertEquals(wallet.getBalance(), 10, 0.0000001);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            wallet.setBalance(-1);
            walletService.update(wallet);
            fail();
        } catch (Exception e) {
        }

    }

    @Test
    public void setStatus() {

        try {
            wallet.setStatus(WalletStatusEnum.BLOCKED);
            assertEquals(wallet.getStatus(), WalletStatusEnum.BLOCKED);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            wallet.setStatus(null);
            walletService.update(wallet);
            fail();
        } catch (Exception e) {
        }

    }

    @Test
    public void equals1() {

        try {
            Wallet wallet2 = wallet;
            assertEquals(wallet, wallet2);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            Wallet wallet2 = wallet.toBuilder().build();
            assertEquals(wallet, wallet2);
            wallet2.setStatus(WalletStatusEnum.BLOCKED);
            assertNotEquals(wallet, wallet2);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void hashCode1() {

        try {
            Wallet wallet2 = wallet;
            assertEquals(wallet.hashCode(), wallet2.hashCode());
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            Wallet wallet2 = wallet.toBuilder().build();
            assertEquals(wallet.hashCode(), wallet2.hashCode());
            wallet2.setStatus(WalletStatusEnum.BLOCKED);
            assertNotEquals(wallet.hashCode(), wallet2.hashCode());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void toString1() {
        System.out.println(wallet);
        try {
            assertTrue(wallet.toString().matches("Wallet\\(id=\\d+, user=User\\(id=\\d+, " +
                    "name=Test, login=Test, password=.+, role=Role\\(id=\\d+, roleEnum=TST\\), " +
                    "updatedAt=\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+\\), currency=Currency\\(id=\\d+, " +
                    "name=TST, rate=0.01\\), balance=0.0, status=ACTIVE\\)"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}