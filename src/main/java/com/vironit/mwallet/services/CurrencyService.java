package com.vironit.mwallet.services;

import com.vironit.mwallet.models.Currency;

import java.util.List;

public interface CurrencyService {

    Currency findById(int id);

    Currency findByName(String name);

    List<Currency> findAll();

    void save(Currency currency);

    void delete(Currency currency);

    void update(Currency currency);

}
