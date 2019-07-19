package com.vironit.mwallet.services.impl;

import com.vironit.mwallet.dao.CurrencyDao;
import com.vironit.mwallet.models.entity.Currency;
import com.vironit.mwallet.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(value = "jdbcTransactionManager")
public class CurrencyServiceImpl implements CurrencyService {

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
