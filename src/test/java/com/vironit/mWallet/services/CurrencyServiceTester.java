package com.vironit.mWallet.services;

import com.vironit.mWallet.models.Currency;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyServiceTester {

    private Currency currency;
    private int id;
    CurrencyService currencyService = new CurrencyService();

    @Test
    void constructorTest() {
        try {
            new CurrencyService();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findAllTest() {
        try {
            List<Currency> currencies;
            currencies = currencyService.findAll();
            assertTrue( currencies != null && currencies.size() > 0 );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void saveTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> currencyService.delete(c));

            currencyService.save(currency);

            currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST") &&
                    Math.abs(currencyOpt.get().getRate() - 0.01) < 0.00001 );

            currencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
        }
    }

    @Test
    void updateTest() {
        try {
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            if ( !currencyOpt.isPresent() ) {
                currency = new Currency("TST", 0.01);
                currencyService.save(currency);
            } else {
                currency = currencyOpt.get();
            }

            currency.setRate(0.02);

            currencyService.update(currency);

            currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST") &&
                    Math.abs(currencyOpt.get().getRate() - 0.02) < 0.00001 );

            currencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
        }
    }

    @Test
    void deleteTest() {
        try {
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            if ( !currencyOpt.isPresent() ) {
                currency = new Currency("TST", 0.01);
                currencyService.save(currency);
            }

            currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            currencyService.delete(currencyOpt.get());

            currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertFalse( currencyOpt.isPresent() );

        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
        }
    }

    @Test
    void findByIdTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            currencyOpt.ifPresent( c -> currencyService.delete(c));

            currencyService.save(currency);
            id = currency.getId();

            currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getId() == id );

            currencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
        }
    }
}