package com.vironit.mWallet.services;

import com.vironit.mWallet.config.SecurityConfig;
import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.models.Role;
import com.vironit.mWallet.models.RoleEnum;
import com.vironit.mWallet.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, SecurityConfig.class})
@WebAppConfiguration
public class SecurityServiceImplTester {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private User user;
    @SuppressWarnings("FieldCanBeLocal")
    private Role role;
    @SuppressWarnings("FieldCanBeLocal")
    private UserDetails userDetails;

    @Test
    public void constructorTest() {
        try {
            new SecurityServiceImpl();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findLoggedInUsernameTest() {
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
                    .setPassword("12345")
                    .setRole(roleOpt.orElse(role))
                    .build();
            if (!roleOpt.isPresent()) {
                roleService.save(role);
            }
            userService.save(user);

            String username = securityService.findLoggedInUsername();

            userService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            userService.delete(user);
        }
    }

    @Test
    public void autoLoginTest() {
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
                    .setPassword("12345")
                    .setRole(roleOpt.orElse(role))
                    .build();
            if (!roleOpt.isPresent()) {
                roleService.save(role);
            }
            userService.save(user);

            securityService.autoLogin("Test", "12345");

            userService.delete(user);
        } catch (Exception e) {
            //TODO fail(e.getMessage());
            userService.delete(user);
        }
    }

}
