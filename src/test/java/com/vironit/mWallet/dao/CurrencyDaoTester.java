package com.vironit.mWallet.dao;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.models.Currency;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class CurrencyDaoTester {

    @Autowired
    private CurrencyDao currencyDao;

    private Currency currency;

    @Test
    public void constructorTest() {
        try {
            new CurrencyDaoImpl();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findAllTest() {
        try {
            List<Currency> currencies;
            currencies = currencyDao.findAll();
            assertTrue(currencies != null && currencies.size() > 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void saveTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = currencyDao.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyDao.delete(c));

            currencyDao.save(currency);

            currencyOpt = currencyDao.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            assertTrue(currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST") &&
                    Math.abs(currencyOpt.get().getRate() - 0.01) < 0.00001);

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
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            if (!currencyOpt.isPresent()) {
                currency = new Currency("TST", 0.01);
                currencyDao.save(currency);
            } else {
                currency = currencyOpt.get();
            }

            currency.setRate(0.02);

            currencyDao.update(currency);

            currencyOpt = currencyDao.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            assertTrue(currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST") &&
                    Math.abs(currencyOpt.get().getRate() - 0.02) < 0.00001);

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
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            if (!currencyOpt.isPresent()) {
                currency = new Currency("TST", 0.01);
                currencyDao.save(currency);
            }

            currencyOpt = currencyDao.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            currencyOpt.ifPresent(c -> currencyDao.delete(c));

            currencyOpt = currencyDao.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            assertFalse(currencyOpt.isPresent());
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
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            currencyOpt.ifPresent(c -> currencyDao.delete(c));

            currencyDao.save(currency);
            int id = currency.getId();

            currencyOpt = currencyDao.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            assertTrue(currencyOpt.isPresent() &&
                    currencyOpt.get().getId() == id);

            currencyDao.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyDao.delete(currency);
        }
    }
}