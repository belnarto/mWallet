package com.vironit.mwallet.service;

import com.vironit.mwallet.model.entity.User;
import com.vironit.mwallet.model.entity.Wallet;
import com.vironit.mwallet.service.exception.WalletServiceException;

import java.util.List;

public interface WalletService {

    Wallet findById(int id);

    List<Wallet> findAllByUser(User user);

    List<Wallet> findAll();

    void save(Wallet wallet) throws WalletServiceException;

    void delete(Wallet wallet);

    void update(Wallet wallet);

    void addBalance(Wallet wallet, double value)
     throws WalletServiceException;

    void reduceBalance(Wallet wallet, double value)
     throws WalletServiceException;

    void transferMoney(Wallet fromWallet, Wallet toWallet, double value)
            throws WalletServiceException;

}
