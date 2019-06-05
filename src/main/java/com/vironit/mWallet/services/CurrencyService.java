package com.vironit.mWallet.services;

import com.vironit.mWallet.dao.CurrencyDao;
import com.vironit.mWallet.dao.CurrencyDaoJDBC;
import com.vironit.mWallet.dao.WalletDao;
import com.vironit.mWallet.models.Currency;
import com.vironit.mWallet.models.Wallet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    CurrencyDao currencyDao = new CurrencyDaoJDBC();

    public CurrencyService() {}

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
        List<Wallet> wallets = WalletDao.findAllByCurrency(currency);
        if (wallets.size() == 0) {
            currencyDao.delete(currency);
        } else {
            throw new IllegalArgumentException("There are "+wallets.size()+" com.vironit.mWallet.wallets associated with this com.vironit.mWallet.currency");
        }
    }

    public void update(Currency currency) {
        currencyDao.update(currency);
    }



}
