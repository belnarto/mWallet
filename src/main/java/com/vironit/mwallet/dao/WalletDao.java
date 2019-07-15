package com.vironit.mwallet.dao;

import com.vironit.mwallet.models.Currency;
import com.vironit.mwallet.models.User;
import com.vironit.mwallet.models.Wallet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletDao extends CrudDao<Wallet> {

    List<Wallet> findAllByUser(User user);

    List<Wallet> findAllByCurrency(Currency currency);

}
