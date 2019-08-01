package com.vironit.mwallet.service;

import com.vironit.mwallet.model.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction findById(int id);

    void save(Transaction transaction);

    void delete(Transaction transaction);

    void update(Transaction transaction);

    List<Transaction> findAll();

}
