package services;

import dao.CurrencyDao;
import dao.CurrencyDaoHibernate;
import dao.CurrencyDaoJDBC;
import dao.WalletDao;
import models.Currency;
import models.Wallet;
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
            throw new IllegalArgumentException("There are "+wallets.size()+" wallets associated with this currency");
        }
    }

    public void update(Currency currency) {
        currencyDao.update(currency);
    }



}
