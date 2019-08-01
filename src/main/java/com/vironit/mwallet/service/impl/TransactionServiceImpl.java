package com.vironit.mwallet.service.impl;

import com.vironit.mwallet.dao.TransactionDao;
import com.vironit.mwallet.model.entity.Transaction;
import com.vironit.mwallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
@Transactional(value = "hibernateTransactionManager")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Override
    public Transaction findById(int id) {
        return transactionDao.findById(id);
    }

    @Override
    public void save(Transaction role) {
        transactionDao.save(role);
    }

    @Override
    public void delete(Transaction role) {
        transactionDao.delete(role);
    }

    @Override
    public void update(Transaction role) {
        transactionDao.update(role);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionDao.findAll();
    }

}
