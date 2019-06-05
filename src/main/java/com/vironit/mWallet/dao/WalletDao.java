package com.vironit.mWallet.dao;

import com.vironit.mWallet.models.Currency;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.vironit.mWallet.models.User;
import org.springframework.stereotype.Repository;
import com.vironit.mWallet.utils.HibernateSessionFactoryUtil;
import com.vironit.mWallet.models.Wallet;

import java.util.List;

@Repository
public class WalletDao {

    public static Wallet findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Wallet.class, id);
    }

    public static void save(Wallet wallet) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(wallet);
        tx1.commit();
        session.close();
    }

    public static void update(Wallet wallet) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(wallet);
        tx1.commit();
        session.close();
    }

    public static void delete(Wallet wallet) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(wallet);
        tx1.commit();
        session.close();
    }

    public static List<Wallet> findAllByUser(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Wallet> wallets = (List<Wallet>) session
                .createQuery("From Wallet where user_id= :id order by id")
                .setParameter("id", user.getId())
                .list();
        session.close();
        return wallets;
    }

    public static List<Wallet> findAllByCurrency(Currency currency) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Wallet> wallets = (List<Wallet>) session
                .createQuery("From Wallet where currency_id= :id order by id")
                .setParameter("id", currency.getId())
                .list();
        session.close();
        return wallets;
    }

    public static List<Wallet> findAll() {

        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Wallet> wallets = (List<Wallet>) session
                                    .createQuery("From Wallet order by id")
                                    .list();
        session.close();

        return wallets;
    }
}
