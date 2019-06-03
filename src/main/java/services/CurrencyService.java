package services;

import dao.CurrencyDao;
import dao.WalletDao;
import models.Currency;
import models.Wallet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    public CurrencyService() {}

    public static Currency findById(int id) {
        return CurrencyDao.findById(id);
    }

    public static Currency findByName(String name) {
        return CurrencyDao.findByName(name);
    }

    public static List<Currency> findAll() {
        return CurrencyDao.findAll();
    }

    public static void save(Currency currency) {
        CurrencyDao.save(currency);
    }

    public static void delete(Currency currency) {
        List<Wallet> wallets = WalletDao.findAllByCurrency(currency);
        if (wallets.size() == 0) {
            CurrencyDao.delete(currency);
        } else {
            throw new IllegalArgumentException("There are "+wallets.size()+" wallets associated with this currency");
        }
    }

    public static void update(Currency currency) {
        CurrencyDao.update(currency);
    }



}
