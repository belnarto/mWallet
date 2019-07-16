package com.vironit.mwallet.services;

import com.vironit.mwallet.config.WebConfig;
import com.vironit.mwallet.models.Role;
import com.vironit.mwallet.models.RoleEnum;
import com.vironit.mwallet.models.User;
import com.vironit.mwallet.services.exception.LoginAlreadyDefinedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
public class UserServiceTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User user;

    @Before
    public void setUp() throws InterruptedException {
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
    }

    @Test
    public void findById() throws LoginAlreadyDefinedException {
        userService.save(user);
        assertEquals(userService.findById(user.getId()), user);
        assertNull(userService.findById(0));
    }

    @Test
    public void findByLogin() throws LoginAlreadyDefinedException {
        userService.save(user);
        assertEquals(userService.findByLogin(user.getLogin()), user);
        assertNull(userService.findByLogin(""));
    }

    @Test
    public void save() throws LoginAlreadyDefinedException {
        userService.save(user);
        assertEquals(userService.findById(user.getId()), user);

    }

    @Test
    public void delete() throws LoginAlreadyDefinedException {
        userService.save(user);
        assertEquals(userService.findById(user.getId()), user);
        userService.delete(user);
        assertNull(userService.findById(user.getId()));
    }

    @Test
    public void update() throws LoginAlreadyDefinedException {
        userService.save(user);
        user.setLogin("Test2");
        userService.update(user);
        assertEquals(userService.findById(user.getId()).getLogin(), "Test2");

        user.setPassword("Test2");
        userService.update(user);
        assertTrue(bCryptPasswordEncoder.matches("Test2", user.getPassword()));
    }

    @Test
    public void findAll() throws LoginAlreadyDefinedException {
        userService.save(user);
        assertTrue(userService.findAll().contains(user));
    }

    @Test
    public void loadUserByUsername() throws LoginAlreadyDefinedException {
        userService.save(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());
        assertEquals(userDetails.getUsername(), user.getLogin());
        assertEquals(userDetails.getPassword(), user.getPassword());
        assertTrue(userDetails.isEnabled());

        //TODO security features
        // https://www.mkyong.com/spring-security/spring-security-limit-login-attempts-example/
    }
}