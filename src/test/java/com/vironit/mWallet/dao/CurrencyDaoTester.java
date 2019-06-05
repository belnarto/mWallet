package com.vironit.mWallet.dao;

import org.junit.jupiter.api.Test;
import com.vironit.mWallet.models.Currency;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyDaoTester {

    private Currency currency;
    CurrencyDao currencyDao = new CurrencyDaoJDBC();

    @Test
    public void constructorTest() {
        try {
            new CurrencyDaoHibernate();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findAllTest() {
        try {
            List<Currency> currencies;
            currencies = currencyDao.findAll();
            assertTrue( currencies != null && currencies.size() > 0 );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void saveTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = currencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> currencyDao.delete(c));

            currencyDao.save(currency);

            currencyOpt = currencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST") &&
                    Math.abs(currencyOpt.get().getRate() - 0.01) < 0.00001 );

            currencyDao.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyDao.delete(currency);
        }
    }

    @Test
    public void updateTest() {
        try {
            Optional<Currency> currencyOpt = currencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            if ( !currencyOpt.isPresent() ) {
                currency = new Currency("TST", 0.01);
                currencyDao.save(currency);
            } else {
                currency = currencyOpt.get();
            }

            currency.setRate(0.02);

            currencyDao.update(currency);

            currencyOpt = currencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST") &&
                    Math.abs(currencyOpt.get().getRate() - 0.02) < 0.00001 );

            currencyDao.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyDao.delete(currency);
        }
    }

    @Test
    public void deleteTest() {
        try {
            Optional<Currency> currencyOpt = currencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            if ( !currencyOpt.isPresent() ) {
                currency = new Currency("TST", 0.01);
                currencyDao.save(currency);
            }

            currencyOpt = currencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            currencyOpt.ifPresent( c -> currencyDao.delete(c));

            currencyOpt = currencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertFalse( currencyOpt.isPresent() );
        } catch (Exception e) {
            fail(e.getMessage());
            currencyDao.delete(currency);
        }
    }

    @Test
    public void findByIdTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = currencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            currencyOpt.ifPresent( c -> currencyDao.delete(c));

            currencyDao.save(currency);
            int id = currency.getId();

            currencyOpt = currencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getId() == id );

            currencyDao.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyDao.delete(currency);
        }
    }
}