package com.vironit.mwallet.dao;

import com.vironit.mwallet.model.entity.Currency;
import com.vironit.mwallet.model.entity.User;
import com.vironit.mwallet.model.entity.Wallet;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface WalletDao extends CrudDao<Wallet> {

    List<Wallet> findAllByUser(User user);

    List<Wallet> findAllByCurrency(Currency currency);

}
