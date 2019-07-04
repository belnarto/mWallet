package com.vironit.mWallet.services;

import com.vironit.mWallet.dao.CurrencyDao;
import com.vironit.mWallet.models.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyDao currencyDao;

    public Currency findById(int id) {
        return currencyDao.findById(id);
    }

    public Currency findByName(String name) {
        return currencyDao.findByName(name);
    }

    public List<Currency> findAll() {
        return currencyDao.findAll();
    }

    public void save(Currency currency) {
        currencyDao.save(currency);
    }

    public void delete(Currency currency) {
        currencyDao.delete(currency);
    }

    public void update(Currency currency) {
        currencyDao.update(currency);
    }

}
