package com.vironit.mWallet.dao.impl;

import com.vironit.mWallet.dao.UserDao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.vironit.mWallet.models.User;
import com.vironit.mWallet.config.HibernateSessionFactoryUtil;

import java.util.List;


public class UserDaoImpl implements UserDao {

    public User findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
    }

    public User findByLogin(String login) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        User user = (User) session
                .createQuery("From User where login= :userLogin ")
                .setParameter("userLogin", login)
                .uniqueResult();
        session.close();
        return user;
    }

    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<User> users = (List<User>) session
                .createQuery("From User order by id")
                .list();
        session.close();

        return users;
    }
}
