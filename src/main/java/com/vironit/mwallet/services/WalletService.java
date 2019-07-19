package com.vironit.mwallet.services;

import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.models.entity.Wallet;
import com.vironit.mwallet.services.exception.WalletServiceException;

import java.util.List;

public interface WalletService {

    Wallet findById(int id);

    List<Wallet> findAllByUser(User user);

    List<Wallet> findAll();

    void save(Wallet wallet);

    void delete(Wallet wallet);

    void update(Wallet wallet);

    void addBalance(Wallet wallet, double value)
     throws WalletServiceException;

    void reduceBalance(Wallet wallet, double value)
     throws WalletServiceException;

    void transferMoney(Wallet fromWallet, Wallet toWallet, double value)
            throws WalletServiceException;

}
