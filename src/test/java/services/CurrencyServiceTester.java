package services;

import models.Currency;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyServiceTester {

    private Currency currency;
    private int id;

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
            currencies = CurrencyService.findAll();
            assertTrue( currencies != null && currencies.size() > 0 );
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void saveTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));

            CurrencyService.save(currency);

            currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST") &&
                    Math.abs(currencyOpt.get().getRate() - 0.01) < 0.00001 );

            CurrencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void updateTest() {
        try {
            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            if ( !currencyOpt.isPresent() ) {
                currency = new Currency("TST", 0.01);
                CurrencyService.save(currency);
            } else {
                currency = currencyOpt.get();
            }

            currency.setRate(0.02);

            CurrencyService.update(currency);

            currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST") &&
                    Math.abs(currencyOpt.get().getRate() - 0.02) < 0.00001 );

            CurrencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void deleteTest() {
        try {
            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            if ( !currencyOpt.isPresent() ) {
                currency = new Currency("TST", 0.01);
                CurrencyService.save(currency);
            }

            currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            CurrencyService.delete(currencyOpt.get());

            currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertFalse( currencyOpt.isPresent() );

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findByIdTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            currencyOpt.ifPresent( c -> CurrencyService.delete(c));

            CurrencyService.save(currency);
            id = currency.getId();

            currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getId() == id );

            CurrencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}