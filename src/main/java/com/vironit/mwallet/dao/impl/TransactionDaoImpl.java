package com.vironit.mwallet.dao.impl;

import com.vironit.mwallet.dao.TransactionDao;
import com.vironit.mwallet.model.entity.Transaction;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "UnnecessaryLocalVariable"})
@Repository
public class TransactionDaoImpl implements TransactionDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Transaction transaction) {
        sessionFactory.getCurrentSession().save(transaction);
    }

    public void update(Transaction transaction) {
        sessionFactory.getCurrentSession().merge(transaction);
    }

    public void delete(Transaction transaction) {
        sessionFactory.getCurrentSession().delete(transaction);
    }

    public Transaction findById(int id) {
        return sessionFactory.getCurrentSession().get(Transaction.class, id);
    }

    @SuppressWarnings({"unchecked", "JpaQlInspection"})
    public List<Transaction> findAll() {
        List<Transaction> transactions = (List<Transaction>) sessionFactory.getCurrentSession()
                .createQuery("From Transaction order by id")
                .list();
        return transactions;
    }

}

