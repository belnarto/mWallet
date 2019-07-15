package com.vironit.mwallet.dao.impl;

import com.vironit.mwallet.config.HibernateSessionFactoryUtil;
import com.vironit.mwallet.dao.UserDao;
import com.vironit.mwallet.models.User;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j2
@Repository
public class UserDaoImpl implements UserDao {

    public void save(User user) {
        log.debug("save user method started with parameter: " + user);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
        log.debug("save user method finished");
    }

    public void update(User user) {
        log.debug("update user method started with parameter: " + user);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
        log.debug("update user method finished");
    }

    public void delete(User user) {
        log.debug("delete user method started with parameter: " + user);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
        log.debug("delete user method finished");
    }

    public User findById(int id) {
        log.debug("find user by id method started with parameter: " + id);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        User user = session.get(User.class, id);
        tx1.commit();
        session.close();
        log.debug("find user by id method finished");
        return user;
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        log.debug("find all users method started");
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<User> users = (List<User>) session
                .createQuery("From User order by id")
                .list();
        session.close();
        log.debug("find all users method finished");
        return users;
    }

    public User findByLogin(String login) {
        log.debug("find user by login method started with parameter: " + login);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        User user = (User) session
                .createQuery("From User where login = :userLogin ")
                .setParameter("userLogin", login)
                .uniqueResult();
        session.close();
        log.debug("find user by login method finished");
        return user;
    }
}
