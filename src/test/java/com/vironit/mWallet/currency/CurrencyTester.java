package com.vironit.mWallet.currency;

import com.vironit.mWallet.models.Currency;
import org.junit.jupiter.api.Test;
import com.vironit.mWallet.services.CurrencyService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("WeakerAccess")
public class CurrencyTester {

    private Currency currency;
    private CurrencyService currencyService = new CurrencyService();

    @SuppressWarnings("WeakerAccess")
    @Test
    public void constructorTest() {
        try {
            new Currency();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new Currency("TST",0.01);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @SuppressWarnings("WeakerAccess")
    @Test
    public void getIdTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = currencyService.findAll()
                    .stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> currencyService.delete(c));

            currencyService.save(currency);

            currencyOpt = currencyService.findAll()
                    .stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getId() == currency.getId() );

            currencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
        }
    }

    @SuppressWarnings("WeakerAccess")
    @Test
    public void setAndGetNameTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> currencyService.delete(c));

            currencyOpt = currencyService.findAll()
                    .stream()
                    .filter( c -> c.getName().equals("TST2") )
                    .findAny();
            currencyOpt.ifPresent( c -> currencyService.delete(c));

            currencyService.save(currency);

            currencyOpt = currencyService.findAll()
                    .stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST") );

            currency.setName("TST2");
            currencyService.update(currency);

            currencyOpt = currencyService.findAll()
                    .stream()
                    .filter( c -> c.getName().equals("TST2") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST2") );

            currencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
        }
    }

    @SuppressWarnings("WeakerAccess")
    @Test
    public void setAndGetRateTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> currencyService.delete(c));

            currencyService.save(currency);

            currencyOpt = currencyService.findAll()
                    .stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    Math.abs(currencyOpt.get().getRate() - 0.01) < 0.000001 );

            currency.setRate(0.02);
            currencyService.update(currency);

            currencyOpt = currencyService.findAll()
                    .stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    Math.abs(currencyOpt.get().getRate() - 0.02) < 0.000001 );

            currencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
        }
    }
}