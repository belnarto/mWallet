package com.vironit.mwallet.service;

import com.vironit.mwallet.model.entity.Currency;

import java.util.List;

public interface CurrencyService {

    Currency findById(int id);

    Currency findByName(String name);

    List<Currency> findAll();

    void save(Currency currency);

    void delete(Currency currency);

    void update(Currency currency);

}
