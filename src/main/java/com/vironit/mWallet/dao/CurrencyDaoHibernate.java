package com.vironit.mWallet.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.vironit.mWallet.models.Currency;
import org.springframework.stereotype.Repository;
import com.vironit.mWallet.utils.HibernateSessionFactoryUtil;

import java.util.List;

@Repository
public class CurrencyDaoHibernate implements CurrencyDao {

    public Currency findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Currency.class, id);
    }

    public Currency findByName(String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Currency currency = (Currency) session
                .createQuery("From Currency where name= :currencyName ")
                .setParameter("currencyName", name)
                .uniqueResult();
        session.close();
        return currency;
    }

    public void save(Currency currency) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(currency);
        tx1.commit();
        session.close();
    }

    public void update(Currency currency) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(currency);
        tx1.commit();
        session.close();
    }

    public void delete(Currency currency) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(currency);
        tx1.commit();
        session.close();
    }

    public List<Currency> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Currency> users = (List<Currency>) session
                                    .createQuery("From Currency order by id")
                                    .list();
        session.close();

        return users;
    }
}
