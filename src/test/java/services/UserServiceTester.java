package services;

import org.junit.jupiter.api.Test;
import models.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTester {

    private User user;
    private int id;

    @Test
    void constructorTest() {
        try {
            new UserService();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void saveTest() {
        try {
            user = new User("Test","Test","Test");
            UserService.save(user);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findByIdTest() {
        try {
            user = new User("Test","Test","Test");
            UserService.save(user);
            id = user.getId();
            assertEquals(UserService.findById(id),user);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void updateTest() {
        try {
            user = new User("Test","Test","Test");
            UserService.save(user);
            id = user.getId();
            user.setName("Test3");
            UserService.update(user);
            assertEquals(UserService.findById(id).getName(),"Test3");
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void deleteTest() {
        try {
            user = new User("Test","Test","Test");
            UserService.save(user);
            id = user.getId();
            UserService.delete(user);
            assertNull(UserService.findById(id));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findAllTest() {
        try {
            user = new User("Test","Test","Test");
            User user2 = new User("Test2","Test2","Test2");
            User user3 = new User("Test3","Test3","Test3");
            List<User> usersToCheck = new ArrayList<>();
            usersToCheck.add(user);
            usersToCheck.add(user2);
            usersToCheck.add(user3);
            UserService.save(user);
            UserService.save(user2);
            UserService.save(user3);
            List<User> users = UserService.findAll();
            assertTrue(users.containsAll(usersToCheck));
            UserService.delete(user);
            UserService.delete(user2);
            UserService.delete(user3);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user = new User("Test","Test","Test");
            User user2 = new User("Test2","Test2","Test2");
            User user3 = new User("Test3","Test3","Test3");
            List<User> usersToCheck = new ArrayList<>();
            usersToCheck.add(user);
            usersToCheck.add(user2);
            usersToCheck.add(user3);
            UserService.save(user);
            UserService.save(user2);
            //UserService.save(user3);
            List<User> users = UserService.findAll();
            assertFalse(users.containsAll(usersToCheck));
            UserService.delete(user);
            UserService.delete(user2);
            UserService.delete(user3);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
