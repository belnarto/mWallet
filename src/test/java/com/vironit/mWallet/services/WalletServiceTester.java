package com.vironit.mWallet.services;

import com.vironit.mWallet.models.Currency;
import org.junit.jupiter.api.Test;
import com.vironit.mWallet.models.User;
import com.vironit.mWallet.models.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class WalletServiceTester {

    private User user;
    private Wallet wallet;
    private Currency currency;
    CurrencyService currencyService = new CurrencyService();

    @Test
    void constructorTest() {
        try {
            new WalletService();
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
            WalletService.save(wallet);

            WalletService.delete(wallet);
            currencyService.delete(currency);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            WalletService.delete(wallet);
            currencyService.delete(currency);
            UserService.delete(user);
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
            WalletService.save(wallet);

            fail();
        } catch (Exception e) {
            WalletService.delete(wallet);
            currencyService.delete(currency);
            UserService.delete(user);
        }

        try {
            user = new User("Test","Test","Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = currencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> currencyService.delete(c));
            //CurrencyService.save(com.vironit.mWallet.currency);

            wallet = new Wallet(user,currency);
            WalletService.save(wallet);

            fail();
        } catch (Exception e) {
            WalletService.delete(wallet);
            currencyService.delete(currency);
            UserService.delete(user);
        }

        try {
            wallet = new Wallet();
            WalletService.save(wallet);
            fail();
        } catch (Exception e) {
            WalletService.delete(wallet);
            currencyService.delete(currency);
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
            WalletService.save(wallet);

            wallet.setCurrency(currency2);
            WalletService.update(wallet);

            assertTrue(WalletService.findAll()
                    .stream()
                    .filter( w -> w.equals(wallet))
                    .findAny()
                    .map( w -> w.getCurrency().getName().equals("TST2"))
                    .orElse(false));

            WalletService.delete(wallet);
            currencyService.delete(currency);
            currencyService.delete(currency2);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            WalletService.delete(wallet);
            currencyService.delete(currency);
            UserService.delete(user);
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
            WalletService.save(wallet);

            WalletService.delete(wallet);

            assertFalse( WalletService.findAll()
                    .stream()
                    .anyMatch( w -> w.equals(wallet)) );

            currencyService.delete(currency);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
            UserService.delete(user);
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
            WalletService.save(wallet);

            UserService.delete(user);

            assertFalse( WalletService.findAll()
                    .stream()
                    .anyMatch( w -> w.equals(wallet)) );

            assertTrue( currencyService.findAll()
                    .stream()
                    .anyMatch( c -> c.equals(currency)) );

            currencyService.delete(currency);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
            UserService.delete(user);
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
            WalletService.save(wallet);

            currencyService.delete(currency);
            fail();


        } catch (Exception e) {
            UserService.delete(user);
            currencyService.delete(currency);
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
            WalletService.save(wallet);
            Wallet wallet2 = new Wallet(user, currency);
            WalletService.save(wallet2);

            List<Wallet> wallets = WalletService.findAll()
                    .stream()
                    .filter( w -> w.getId() == wallet.getId() || w.getId() == wallet2.getId())
                    .collect(Collectors.toList());

            assertTrue( wallets.size() == 2 );

            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
            UserService.delete(user);
        }
    }

    @Test
    void findAllByUserTest() {

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
            wallet.setBalance(0.2);
            WalletService.save(wallet);

            Currency currency2 = new Currency("TST2", 0.02);
            currencyOpt = currencyService.findAll().stream()
                    .filter(c -> c.getName().equals("TST2"))
                    .findAny();
            currencyOpt.ifPresent(c -> currencyService.delete(c));
            currencyService.save(currency2);

            Wallet wallet2 = new Wallet(user, currency2);
            wallet2.setBalance(0.1);
            WalletService.save(wallet2);

            List<Wallet> wallets = WalletService.findAllByUser(user);

            assertEquals( wallets.size(), 2 );

            WalletService.delete(wallet);
            WalletService.delete(wallet2);
            UserService.delete(user);
            currencyService.delete(currency);
            currencyService.delete(currency2);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
            UserService.delete(user);
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
            WalletService.save(wallet);
            Wallet wallet2 = new Wallet(user, currency);
            WalletService.save(wallet2);

            List<Wallet> wallets = WalletService.findAll()
                    .stream()
                    .filter( w -> w.equals(wallet) || w.equals(wallet2))
                    .collect(Collectors.toList());

            assertTrue( wallets.size() == 2 );

            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
            currencyService.delete(currency);
            UserService.delete(user);
        }
    }
}