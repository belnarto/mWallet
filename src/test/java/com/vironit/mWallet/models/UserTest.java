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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class UserTest {

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

    @Before
    public void setUp() throws InterruptedException {
        Wallet wallet;

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

        currencyService.delete(currencyService.findByName("TST"));

        Optional<Role> roleOpt = roleService.findAll().stream()
                .filter(r -> r.getRoleEnum().toString().equals("TST"))
                .findAny();
        roleOpt.ifPresent(roleService::delete);
    }

    @Test
    public void getId() {
        try {
            assertTrue(user.getId() >= 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getName() {
        try {
            assertEquals(user.getName(), "Test");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getLogin() {
        try {
            assertEquals(user.getLogin(), "Test");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getPassword() {
        try {
            assertTrue(bCryptPasswordEncoder.matches("Test", user.getPassword()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getRole() {
        try {
            assertEquals(user.getRole(), roleService.findByName("TST"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getWallets() {
        try {
            assertEquals(user.getWallets().size(), 0);
            Set<Wallet> wallets = new HashSet<>();
            Wallet wallet1 = new Wallet.WalletBuilder()
                    .user(user)
                    .currency(currency)
                    .build();
            walletService.save(wallet1);
            wallets.add(wallet1);
            Wallet wallet2 = new Wallet.WalletBuilder()
                    .user(user)
                    .currency(currency)
                    .build();
            walletService.save(wallet2);
            wallets.add(wallet2);
            user.setWallets(wallets);
            userService.update(user);
            assertEquals(user.getWallets().size(), 2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getUpdatedAt() {
        try {
            assertTrue(user.getUpdatedAt().isBefore(LocalDateTime.now()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setId() {
        try {
            user.setId(0);
            assertEquals(user.getId(), 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user.setId(-1);
            userService.update(user);
            fail();
        } catch (Exception e) {
        }

    }

    @Test
    public void setName() {
        try {
            user.setName("Test2");
            assertEquals(user.getName(), "Test2");
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user.setName("");
            userService.update(user);
            fail();
        } catch (Exception e) {
        }

        try {
            user.setName("123");
            userService.update(user);
            fail();
        } catch (Exception e) {
        }

        try {
            user.setName("0123456789012345678901234567890123456789012345678901234567890");
            userService.update(user);
            fail();
        } catch (Exception e) {
        }

        try {
            user.setName(null);
            userService.update(user);
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void setLogin() {
        try {
            user.setLogin("Test2");
            assertEquals(user.getLogin(), "Test2");
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user.setLogin("");
            userService.update(user);
            fail();
        } catch (Exception e) {
        }

        try {
            user.setLogin("123");
            userService.update(user);
            fail();
        } catch (Exception e) {
        }

        try {
            user.setLogin("0123456789012345678901234567890123456789012345678901234567890");
            userService.update(user);
            fail();
        } catch (Exception e) {
        }

        try {
            user.setLogin(null);
            userService.update(user);
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void setPassword() {
        try {
            user.setPassword("Test2");
            userService.update(user);
            assertTrue(bCryptPasswordEncoder.matches("Test2", user.getPassword()));
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user.setPassword("");
            userDao.update(user);
            fail();
        } catch (Exception e) {
        }

        try {
            user.setPassword("123");
            userDao.update(user);
            fail();
        } catch (Exception e) {
        }

        try {
            user.setPassword("0123456789012345678901234567890123456789012345678901234567890");
            userDao.update(user);
            fail();
        } catch (Exception e) {
        }

        try {
            user.setPassword(null);
            userDao.update(user);
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void setRole() {
        try {
            user.setRole(roleService.findByName("DEFAULT"));
            assertEquals(user.getRole(), roleService.findByName("DEFAULT"));
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user.setRole(null);
            userService.update(user);
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void setWallets() {
        try {
            Set<Wallet> wallets = new HashSet<>();
            Wallet wallet1 = new Wallet.WalletBuilder()
                    .user(user)
                    .currency(currency)
                    .build();
            walletService.save(wallet1);
            wallets.add(wallet1);
            Wallet wallet2 = new Wallet.WalletBuilder()
                    .user(user)
                    .currency(currency)
                    .build();
            walletService.save(wallet2);
            wallets.add(wallet2);
            user.setWallets(wallets);
            userService.update(user);
            assertEquals(user.getWallets().size(), 2);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void equals1() {
        try {
            User user2 = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            assertEquals(user, user2);

            user2.setName("Test2");
            assertNotEquals(user, user2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void hashCode1() {
        try {
            User user2 = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            assertEquals(user, user2);
            assertEquals(user.hashCode(), user2.hashCode());

            user2.setName("Test2");
            assertNotEquals(user.hashCode(), user2.hashCode());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void toString1() {
        try {
            assertTrue(user.toString().matches("User\\(id=\\d+, name=Test, login=Test, password=.+, " +
                    "role=Role\\(id=\\d+, roleEnum=TST\\), " +
                    "updatedAt=\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+\\)"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void builder() {
        try {
            User user2 = User.builder()
                    .login("Test")
                    .name("Test")
                    .password("Test")
                    .role(role)
                    .build();

            assertEquals(user.getName(), user2.getName());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void toBuilder() {
        try {
            User user2 = user.toBuilder()
                    .build();

            assertEquals(user, user2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}