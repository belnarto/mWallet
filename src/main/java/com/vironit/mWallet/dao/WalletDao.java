package com.vironit.mWallet.dao;

import com.vironit.mWallet.models.Currency;
import com.vironit.mWallet.models.User;
import com.vironit.mWallet.models.Wallet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletDao extends CrudDao<Wallet> {

    List<Wallet> findAllByUser(User user);

    List<Wallet> findAllByCurrency(Currency currency);

}
