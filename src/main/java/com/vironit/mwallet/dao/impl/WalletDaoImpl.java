package com.vironit.mwallet.dao.impl;


import com.vironit.mwallet.dao.WalletDao;
import com.vironit.mwallet.models.entity.Currency;
import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.models.entity.Wallet;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "UnnecessaryLocalVariable"})
@Repository
public class WalletDaoImpl implements WalletDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Wallet findById(int id) {
        return sessionFactory.getCurrentSession().get(Wallet.class, id);
    }

    public void save(Wallet wallet) {
        sessionFactory.getCurrentSession().save(wallet);
    }

    public void update(Wallet wallet) {
        sessionFactory.getCurrentSession().merge(wallet);
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

    @SuppressWarnings({"unchecked", "JpaQlInspection"})
    public List<Wallet> findAll() {
        List<Wallet> wallets = (List<Wallet>) sessionFactory.getCurrentSession()
                .createQuery("From Wallet order by id")
                .list();
        return wallets;
    }
}
