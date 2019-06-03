package dao;

import org.junit.jupiter.api.Test;
import models.Currency;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyDaoTester {

    private Currency currency;

    @Test
    public void constructorTest() {
        try {
            new CurrencyDao();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findAllTest() {
        try {
            List<Currency> currencies;
            currencies = CurrencyDao.findAll();
            assertTrue( currencies != null && currencies.size() > 0 );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void saveTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = CurrencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyDao.delete(c));

            CurrencyDao.save(currency);

            currencyOpt = CurrencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST") &&
                    Math.abs(currencyOpt.get().getRate() - 0.01) < 0.00001 );

            CurrencyDao.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void updateTest() {
        try {
            Optional<Currency> currencyOpt = CurrencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            if ( !currencyOpt.isPresent() ) {
                currency = new Currency("TST", 0.01);
                CurrencyDao.save(currency);
            } else {
                currency = currencyOpt.get();
            }

            currency.setRate(0.02);

            CurrencyDao.update(currency);

            currencyOpt = CurrencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST") &&
                    Math.abs(currencyOpt.get().getRate() - 0.02) < 0.00001 );

            CurrencyDao.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteTest() {
        try {
            Optional<Currency> currencyOpt = CurrencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            if ( !currencyOpt.isPresent() ) {
                currency = new Currency("TST", 0.01);
                CurrencyDao.save(currency);
            }

            currencyOpt = CurrencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            currencyOpt.ifPresent( c -> CurrencyDao.delete(c));

            currencyOpt = CurrencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertFalse( currencyOpt.isPresent() );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findByIdTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = CurrencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            currencyOpt.ifPresent( c -> CurrencyDao.delete(c));

            CurrencyDao.save(currency);
            int id = currency.getId();

            currencyOpt = CurrencyDao.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getId() == id );

            CurrencyDao.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}