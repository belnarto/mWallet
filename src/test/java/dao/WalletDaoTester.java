package dao;

import models.Currency;
import org.junit.jupiter.api.Test;
import services.CurrencyService;
import services.UserService;
import models.User;
import models.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class WalletDaoTester {

    private Wallet wallet;
    private User user;
    private Currency currency;
    CurrencyService currencyService = new CurrencyService();

    @Test
    public void constructorTest() {
        try {
            new WalletDao();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void saveTest() {
        try {
            user = new User("Test","Test","Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user,currency);
            WalletDao.save(wallet);

            WalletDao.delete(wallet);
            currencyService.delete(currency);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user = new User("Test","Test","Test");
            //UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user,currency);
            WalletDao.save(wallet);

            fail();
        } catch (Exception e) {
            currencyService.delete(currency);
        }

        try {
            user = new User("Test","Test","Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> currencyService.delete(c));
            //CurrencyService.save(currency);

            wallet = new Wallet(user,currency);
            WalletDao.save(wallet);

            fail();
        } catch (Exception e) {
            UserService.delete(user);
        }

        try {
            wallet = new Wallet();
            WalletDao.save(wallet);
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    void updateTest() {
        try {
            user = new User("Test","Test","Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> currencyService.delete(c));
            currencyService.save(currency);

            Currency currency2 = new Currency("TST2", 0.02);
            Optional<Currency> currencyOpt2 = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST2") )
                    .findAny();
            currencyOpt2.ifPresent( c -> currencyService.delete(c));
            currencyService.save(currency2);

            wallet = new Wallet(user,currency);
            WalletDao.save(wallet);

            wallet.setCurrency(currency2);
            WalletDao.update(wallet);

            assertTrue(WalletDao.findAll()
                    .stream()
                    .filter( w -> w.equals(wallet))
                    .findAny()
                    .map( w -> w.getCurrency().getName().equals("TST2"))
                    .orElse(false));

            WalletDao.delete(wallet);
            currencyService.delete(currency);
            currencyService.delete(currency2);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void deleteTest() {
        try {
            user = new User("Test","Test","Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            WalletDao.save(wallet);

            WalletDao.delete(wallet);

            assertFalse( WalletDao.findAll()
                    .stream()
                    .anyMatch( w -> w.equals(wallet)) );

            currencyService.delete(currency);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user = new User("Test","Test","Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            WalletDao.save(wallet);

            UserService.delete(user);

            assertFalse( WalletDao.findAll()
                    .stream()
                    .anyMatch( w -> w.equals(wallet)) );

            assertTrue( currencyService.findAll()
                    .stream()
                    .anyMatch( c -> c.equals(currency)) );

            currencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user = new User("Test","Test","Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            WalletDao.save(wallet);

            currencyService.delete(currency);

            fail();

        } catch (Exception e) {
            UserService.delete(user);
            currencyService.delete(currency);
        }
    }

    @Test
    void findAllTest() {

        try {
            user = new User("Test","Test","Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            WalletDao.save(wallet);
            Wallet wallet2 = new Wallet(user, currency);
            WalletDao.save(wallet2);

            List<Wallet> wallets = WalletDao.findAll()
                    .stream()
                    .filter( w -> w.equals(wallet) || w.equals(wallet2))
                    .collect(Collectors.toList());

            assertTrue( wallets.size() == 2 );

            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findByIdTest() {

        try {
            user = new User("Test","Test","Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency);

            wallet = new Wallet(user, currency);
            WalletDao.save(wallet);
            Wallet wallet2 = new Wallet(user, currency);
            WalletDao.save(wallet2);

            List<Wallet> wallets = WalletDao.findAll()
                    .stream()
                    .filter( w -> w.getId() == wallet.getId() || w.getId() == wallet2.getId())
                    .collect(Collectors.toList());

            assertTrue( wallets.size() == 2 );

            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
