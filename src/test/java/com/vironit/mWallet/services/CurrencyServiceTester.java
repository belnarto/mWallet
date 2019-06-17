package com.vironit.mWallet.services;

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
public class CurrencyServiceTester {

    @Autowired
    private CurrencyService currencyService;

    private Currency currency;

    @Test
    public void constructorTest() {
        try {
            new CurrencyService();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findAllTest() {
        try {
            List<Currency> currencies;
            currencies = currencyService.findAll();
            assertTrue(currencies != null && currencies.size() > 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void saveTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));

            currencyService.save(currency);

            currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            assertTrue(currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST") &&
                    Math.abs(currencyOpt.get().getRate() - 0.01) < 0.00001);

            currencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
        }
    }

    @Test
    public void updateTest() {
        try {
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            if (!currencyOpt.isPresent()) {
                currency = new Currency("TST", 0.01);
                currencyService.save(currency);
            } else {
                currency = currencyOpt.get();
            }

            currency.setRate(0.02);

            currencyService.update(currency);

            currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            assertTrue(currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST") &&
                    Math.abs(currencyOpt.get().getRate() - 0.02) < 0.00001);

            currencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
        }
    }

    @Test
    public void deleteTest() {
        try {
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            if (!currencyOpt.isPresent()) {
                currency = new Currency("TST", 0.01);
                currencyService.save(currency);
            }

            currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            currencyService.delete(currencyOpt.orElse(null));

            currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            assertFalse(currencyOpt.isPresent());

        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
        }
    }

    @Test
    public void findByIdTest() {
        int id;
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            currencyOpt.ifPresent(c -> currencyService.delete(c));

            currencyService.save(currency);
            id = currency.getId();

            currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            assertTrue(currencyOpt.isPresent() &&
                    currencyOpt.get().getId() == id);

            currencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
        }
    }
}