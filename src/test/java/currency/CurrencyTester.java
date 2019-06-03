package currency;

import models.Currency;
import org.junit.jupiter.api.Test;
import services.CurrencyService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyTester {

    Currency currency;

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

    @Test
    public void getIdTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = CurrencyService.findAll()
                    .stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));

            CurrencyService.save(currency);

            currencyOpt = CurrencyService.findAll()
                    .stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getId() == currency.getId() );

            CurrencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setAndGetNameTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));

            currencyOpt = CurrencyService.findAll()
                    .stream()
                    .filter( c -> c.getName().equals("TST2") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));

            CurrencyService.save(currency);

            currencyOpt = CurrencyService.findAll()
                    .stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST") );

            currency.setName("TST2");
            CurrencyService.update(currency);

            currencyOpt = CurrencyService.findAll()
                    .stream()
                    .filter( c -> c.getName().equals("TST2") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    currencyOpt.get().getName().equals("TST2") );

            CurrencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setAndGetRateTest() {
        try {
            currency = new Currency("TST", 0.01);

            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));

            CurrencyService.save(currency);

            currencyOpt = CurrencyService.findAll()
                    .stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    Math.abs(currencyOpt.get().getRate() - 0.01) < 0.000001 );

            currency.setRate(0.02);
            CurrencyService.update(currency);

            currencyOpt = CurrencyService.findAll()
                    .stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();

            assertTrue( currencyOpt.isPresent() &&
                    Math.abs(currencyOpt.get().getRate() - 0.02) < 0.000001 );

            CurrencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}