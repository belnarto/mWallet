package com.vironit.mwallet.dao.impl;


import com.vironit.mwallet.dao.WalletDao;
import com.vironit.mwallet.models.Currency;
import com.vironit.mwallet.models.User;
import com.vironit.mwallet.models.Wallet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WalletDaoImpl implements WalletDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Wallet findById(int id) {
        Wallet wallet = sessionFactory.getCurrentSession().get(Wallet.class, id);
        if (wallet != null) {
            sessionFactory.getCurrentSession().evict(wallet); // detaching object from persistence session
        }
            return wallet;
    }

    public void save(Wallet wallet) {
        sessionFactory.getCurrentSession().save(wallet);
    }

    public void update(Wallet wallet) {
        sessionFactory.getCurrentSession().update(wallet);
    }

    public void delete(Wallet wallet) {
        sessionFactory.getCurrentSession().delete(wallet);
    }

    @SuppressWarnings({"unchecked", "JpaQlInspection"})
    public List<Wallet> findAllByUser(User user) {
        List<Wallet> wallets = (List<Wallet>) sessionFactory.getCurrentSession()
                .createQuery("From Wallet where user_id= :id order by id")
                .setParameter("id", user.getId())
                .list();
        return wallets;
    }

    @SuppressWarnings({"unchecked", "JpaQlInspection"})
    public List<Wallet> findAllByCurrency(Currency currency) {
        List<Wallet> wallets = (List<Wallet>) sessionFactory.getCurrentSession()
                .createQuery("From Wallet where currency_id= :id order by id")
                .setParameter("id", currency.getId())
                .list();
        return wallets;
    }

    @SuppressWarnings("unchecked")
    public List<Wallet> findAll() {
        List<Wallet> wallets = (List<Wallet>) sessionFactory.getCurrentSession()
                .createQuery("From Wallet order by id")
                .list();
        return wallets;
    }
}
