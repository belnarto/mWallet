package com.vironit.mwallet.dao.impl;

import com.vironit.mwallet.dao.UserDao;
import com.vironit.mwallet.models.User;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j2
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    public User findById(int id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    public List<User> findAll() {
        List<User> users = (List<User>) sessionFactory.getCurrentSession()
                .createQuery("From User order by id")
                .list();
        return users;
    }

    public User findByLogin(String login) {
        User user = (User) sessionFactory.getCurrentSession()
                .createQuery("From User where login = :userLogin ")
                .setParameter("userLogin", login)
                .uniqueResult();
        return user;
    }
}
