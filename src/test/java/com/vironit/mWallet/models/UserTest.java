package com.vironit.mWallet.models;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.services.CurrencyService;
import com.vironit.mWallet.services.RoleService;
import com.vironit.mWallet.services.UserService;
import com.vironit.mWallet.services.WalletService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void setUp() {
        Optional<User> userOpt = userService.findAll().stream()
                .filter(u -> u.getLogin().equals("Test"))
                .findAny();
        userOpt.ifPresent(userService::delete);

        Role role = new Role(RoleEnum.TST);
        Optional<Role> roleOpt = roleService.findAll().stream()
                .filter(r -> r.getRoleEnum().toString().equals("TST"))
                .findAny();
        if (!roleOpt.isPresent()) {
            roleService.save(role);
        }

        User user = User.builder()
                .login("Test")
                .name("Test")
                .password("Test")
                .role(roleOpt.orElse(role))
                .updatedAt(LocalDateTime.now())
                .build();
        userService.save(user);

        Currency currency = new Currency("TST", 0.01);
        Optional<Currency> currencyOpt = currencyService.findAll().stream()
                .filter(c -> c.getName().equals("TST"))
                .findAny();
        currencyOpt.ifPresent(c -> currencyService.delete(c));
        currencyService.save(currency);

        Wallet wallet = new Wallet(user, currency);
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
    }

    @Test
    public void getId() {
        try {
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            assertTrue(user.getId() >= 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getName() {
        try {
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            assertEquals(user.getName(), "Test");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getLogin() {
        try {
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            assertEquals(user.getLogin(), "Test");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getPassword() {
        try {
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            assertTrue(bCryptPasswordEncoder.matches("Test", user.getPassword()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getRole() {
        try {
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            assertEquals(user.getRole(), roleService.findByName("TST"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getWallets() {
    }

    @Test
    public void getUpdatedAt() {
        try {
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            assertTrue(user.getUpdatedAt().isBefore(LocalDateTime.now()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setId() {
        try {
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            user.setId(0);
            assertEquals(user.getId(), 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setName() {
        try {
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            user.setName("Test2");
            assertEquals(user.getName(), "Test2");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setLogin() {
        try {
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            user.setLogin("Test2");
            assertEquals(user.getLogin(), "Test2");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setPassword() {
        try {
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            user.setPassword("Test2");
            userService.update(user);
            assertTrue(bCryptPasswordEncoder.matches("Test2", user.getPassword()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setRole() {
        try {
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            user.setRole(roleService.findByName("DEFAULT"));
            assertEquals(user.getRole(), roleService.findByName("DEFAULT"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setWallets() {
    }

    @Test
    public void equals1() {
        try {
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            User user2 = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            assertEquals(user,user2);

            user2.setName("Test2");
            assertNotEquals(user, user2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void hashCode1() {
        try {
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
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
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);
            assertTrue(user.toString().matches("User\\(id=\\d+, name=Test, login=Test, password=.+, " +
                    "role=Role\\{id=\\d+, roleEnum=TST}, updatedAt=\\d+-\\d+-\\d+T\\d+:\\d+:\\d+.\\d+, wallets=\\[.*]\\)"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void builder() {
        try {
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);

            Role role = new Role(RoleEnum.TST);
            Optional<Role> roleOpt = roleService.findAll().stream()
                    .filter(r -> r.getRoleEnum().toString().equals("TST"))
                    .findAny();
            User user2 = User.builder()
                    .login("Test")
                    .name("Test")
                    .password("Test")
                    .role(roleOpt.orElse(role))
                    .build();

            assertEquals(user.getName(), user2.getName());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void toBuilder() {
        try {
            User user = userService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny().orElseThrow(Exception::new);

            User user2 = user.toBuilder()
                    .build();

            assertEquals(user, user2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}