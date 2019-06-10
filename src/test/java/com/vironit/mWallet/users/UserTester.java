package com.vironit.mWallet.users;

import com.vironit.mWallet.models.User;
import org.junit.jupiter.api.Test;
import com.vironit.mWallet.services.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserTester {

    private User user;
    private User user2;
    private int id;

    @Test
    void constructorTest() {
        try {
            new User();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new User("Test", "Test", "Test");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getIdTest() {
        try {
            user = new User("Test", "Test", "Test");
            Optional<User> userOpt = UserService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny();
            userOpt.ifPresent(UserService::delete);
            user.setRole("ROLE_USER");
            UserService.save(user);
            id = user.getId();
            assertTrue(id > 0);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            UserService.delete(user);
        }
    }

    @Test
    void setAndGetNameTest() {
        try {
            user = new User("Test", "Test", "Test");
            Optional<User> userOpt = UserService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny();
            userOpt.ifPresent(UserService::delete);
            user.setRole("ROLE_USER");
            UserService.save(user);
            id = user.getId();
            user.setName("Test2");
            UserService.update(user);
            boolean result = UserService.findAll()
                    .stream()
                    .filter(u -> u.getId() == id)
                    .findFirst()
                    .orElseThrow(null)
                    .getName()
                    .equals("Test2");
            assertTrue(result);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            UserService.delete(user);
        }
    }

    @Test
    void equalsTest() {
        try {
            user = new User("Test", "Test", "Test");
            Optional<User> userOpt = UserService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny();
            userOpt.ifPresent(UserService::delete);
            user.setRole("ROLE_USER");
            user = new User("Test2", "Test2", "Test2");
            userOpt = UserService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test2"))
                    .findAny();
            userOpt.ifPresent(UserService::delete);
            user.setRole("ROLE_USER");
            assertNotEquals(user,user2);
        } catch (Exception e) {
            fail(e.getMessage());
            UserService.delete(user);
        }

        try {
            user = new User("Test", "Test", "Test");
            Optional<User> userOpt = UserService.findAll().stream()
                    .filter(u -> u.getLogin().equals("Test"))
                    .findAny();
            userOpt.ifPresent(UserService::delete);
            user.setRole("ROLE_USER");
            user2 = user;
            assertEquals(user,user2);
        } catch (Exception e) {
            fail(e.getMessage());
            UserService.delete(user);
        }
    }

}
