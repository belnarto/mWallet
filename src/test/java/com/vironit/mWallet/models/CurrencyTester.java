package com.vironit.mWallet.models;

import com.vironit.mWallet.config.WebConfig;
import com.vironit.mWallet.services.CurrencyService;

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
public class CurrencyTester {

    @Autowired
    private CurrencyService currencyService;

    private Currency currency;

    @Test
    public void constructorTest() {
        try {
            new Currency();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new Currency("TST", 0.01);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getIdTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = currencyService.findAll()
                    .stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));

            currencyService.save(currency);

            currencyOpt = currencyService.findAll()
                    .stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            assertTrue(currencyOpt.isPresent() &&
                    currencyOpt.get().getId() == currency.getId());

            currencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
        }
    }

    @Test
    public void setAndGetNameTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));

            currencyOpt = currencyService.findAll()
                    .stream()
                    .filter(c -> c.getName().equals("TST2"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));

            currencyService.save(currency);

            currencyOpt = currencyService.findAll()
                    .stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            assertTrue(currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST"));

            currency.setName("TST2");
            currencyService.update(currency);

            currencyOpt = currencyService.findAll()
                    .stream()
                    .filter(c -> c.getName().equals("TST2"))
                    .findAny();

            assertTrue(currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST2"));

            currencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
        }
    }

    @Test
    public void setAndGetRateTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));

            currencyService.save(currency);

            currencyOpt = currencyService.findAll()
                    .stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            assertTrue(currencyOpt.isPresent() &&
                    Math.abs(currencyOpt.get().getRate() - 0.01) < 0.000001);

            currency.setRate(0.02);
            currencyService.update(currency);

            currencyOpt = currencyService.findAll()
                    .stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();

            assertTrue(currencyOpt.isPresent() &&
                    Math.abs(currencyOpt.get().getRate() - 0.02) < 0.000001);

            currencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
        }
    }
}