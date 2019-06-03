package dao;

import org.junit.jupiter.api.Test;
import models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTester {

    private User user;

    @Test
    void constructorTest() {
        try {
            new UserDao();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findAllTest() {
        try {
            User user1 = new User("Test");
            User user2 = new User("Test2");
            User user3 = new User("Test3");

            List<User> usersToCheck = new ArrayList<>();
            usersToCheck.add(user1);
            usersToCheck.add(user2);
            usersToCheck.add(user3);

            UserDao.save(user1);
            UserDao.save(user2);
            UserDao.save(user3);

            List<User> users = UserDao.findAll();

            assertTrue(users.containsAll(usersToCheck));

            UserDao.delete(user1);
            UserDao.delete(user2);
            UserDao.delete(user3);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    void saveTest() {
        try {
            user = new User("Test");
            UserDao.save(user);

            List<User> users = UserDao.findAll().stream()
                    .filter( u -> u.getName().equals("Test"))
                    .collect(Collectors.toList());

            assertTrue( users.contains(user) );

            UserDao.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findByIdTest() {
        try {
            user = new User("Test");
            UserDao.save(user);

            assertEquals( UserDao.findById(user.getId()), user );

            UserDao.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void updateTest() {
        try {
            user = new User("Test");
            UserDao.save(user);

            assertEquals( UserDao.findById(user.getId()).getName(),"Test" );

            user.setName("Test2");
            UserDao.update(user);

            assertEquals( UserDao.findById(user.getId()).getName(),"Test2" );

            UserDao.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void deleteTest() {
        try {
            user = new User("Test");
            UserDao.save(user);

            assertNotNull( UserDao.findById(user.getId()) );

            UserDao.delete(user);

            assertNull( UserDao.findById(user.getId()) );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
