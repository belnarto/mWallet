package com.vironit.mWallet.dao;

import com.vironit.mWallet.models.Currency;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyDao extends CrudDao<Currency> {

    Currency findById(int id);

    Currency findByName(String name);

    void save(Currency currency);

    void update(Currency currency);

    void delete(Currency currency);

    List<Currency> findAll();

}
