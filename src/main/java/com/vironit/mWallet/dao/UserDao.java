package com.vironit.mWallet.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.vironit.mWallet.models.User;
import org.springframework.stereotype.Repository;
import com.vironit.mWallet.utils.HibernateSessionFactoryUtil;

import java.util.List;

@Repository
public class UserDao {

    public static User findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
    }

    public static User findByLogin(String login) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        User user = (User) session
                .createQuery("From User where login= :userLogin ")
                .setParameter("userLogin", login)
                .uniqueResult();
        session.close();
        return user;
    }

    public static void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public static void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public static void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    public static List<User> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<User> users = (List<User>) session
                                    .createQuery("From User order by id")
                                    .list();
        session.close();

        return users;
    }
}
