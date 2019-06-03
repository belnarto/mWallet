package wallets;

import models.Currency;
import models.Wallet;
import org.junit.jupiter.api.Test;
import services.CurrencyService;
import services.UserService;
import services.WalletService;
import models.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class WalletTester {

    private User user;
    private int id;
    private Currency currency;
    private Wallet wallet;

    @Test
    void constructorTest() {
        try {
            new Wallet();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user = new User("Test");
            currency = new Currency("TST", 0.01);
            wallet = new Wallet(user, currency);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getIdTest() {
        try {
            user = new User("Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency);

            wallet = new Wallet(user,currency);
            WalletService.save(wallet);
            id = wallet.getId();

            assertTrue(WalletService.findAll()
                    .stream()
                    .anyMatch( w -> w.getId() == id));

            WalletService.delete(wallet);
            CurrencyService.delete(currency);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void setAndGetUserTest() {
        try {
            user = new User("Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency);

            wallet = new Wallet(user,currency);
            WalletService.save(wallet);

            assertTrue(WalletService.findAll()
                    .stream()
                    .anyMatch( w -> w.getUser().equals(user)));

            WalletService.delete(wallet);
            CurrencyService.delete(currency);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user = new User("Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency);

            wallet = new Wallet(user,currency);
            WalletService.save(wallet);
            id = wallet.getId();

            assertTrue(WalletService.findAll()
                    .stream()
                    .anyMatch( w -> w.getUser().equals(user)));

            User user2 = new User("Test2");
            UserService.save(user2);

            wallet.setUser(user2);
            WalletService.update(wallet);

            assertTrue(WalletService.findAll()
                    .stream()
                    .anyMatch( w -> w.getUser().equals(user2) && w.getId() == id));

            WalletService.delete(wallet);
            CurrencyService.delete(currency);
            UserService.delete(user);
            UserService.delete(user2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void setAndGetCurrencyTest() {
        try {
            user = new User("Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency);

            wallet = new Wallet(user,currency);
            WalletService.save(wallet);

            assertTrue(WalletService.findAll()
                    .stream()
                    .anyMatch( w -> w.getCurrency().equals(currency)));

            WalletService.delete(wallet);
            CurrencyService.delete(currency);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user = new User("Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency);

            wallet = new Wallet(user,currency);
            WalletService.save(wallet);
            id = wallet.getId();

            assertTrue(WalletService.findAll()
                    .stream()
                    .anyMatch( w -> w.getUser().equals(user)));

            Currency currency2 = new Currency("TST2", 0.02);
            currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST2") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency2);

            wallet.setCurrency(currency2);
            WalletService.update(wallet);

            assertTrue(WalletService.findAll()
                    .stream()
                    .anyMatch( w -> w.getCurrency().equals(currency2) && w.getId() == id));

            WalletService.delete(wallet);
            CurrencyService.delete(currency);
            CurrencyService.delete(currency2);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void setAndGetBalanceTest() {
        try {
            user = new User("Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency);

            wallet = new Wallet(user,currency);
            wallet.setBalance(0.03);
            WalletService.save(wallet);
            id = wallet.getId();

            assertTrue(WalletService.findAll()
                    .stream()
                    .anyMatch( w -> w.getId() == id && Math.abs(w.getBalance() - 0.03) < 0.000001));

            WalletService.delete(wallet);
            CurrencyService.delete(currency);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user = new User("Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency);

            wallet = new Wallet(user,currency);
            wallet.setBalance(0.03);
            WalletService.save(wallet);
            id = wallet.getId();

            assertTrue(WalletService.findAll()
                    .stream()
                    .anyMatch( w -> w.getId() == id && Math.abs(w.getBalance() - 0.03) < 0.000001));

            wallet.setBalance(wallet.getBalance() + 0.02);
            WalletService.update(wallet);

            assertTrue(WalletService.findAll()
                    .stream()
                    .anyMatch( w -> w.getId() == id && Math.abs(w.getBalance() - 0.05) < 0.000001));

            WalletService.delete(wallet);
            CurrencyService.delete(currency);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void equalsTest() {
        try {
            user = new User("Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency);

            wallet = new Wallet(user,currency);
            wallet.setBalance(0.03);
            WalletService.save(wallet);
            id = wallet.getId();

            assertTrue(WalletService.findAll()
                    .stream()
                    .anyMatch( w-> w.getId()==id && w.equals(wallet)));

            WalletService.delete(wallet);
            CurrencyService.delete(currency);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user = new User("Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency);

            wallet = new Wallet(user,currency);
            wallet.setBalance(0.03);
            WalletService.save(wallet);
            id = wallet.getId();

            Wallet wallet2 = new Wallet(user,currency);
            wallet.setBalance(0.03);
            WalletService.save(wallet2);

            assertFalse(WalletService.findAll()
                    .stream()
                    .anyMatch( w-> w.getId()==id && w.equals(wallet2)));

            WalletService.delete(wallet);
            WalletService.delete(wallet2);
            CurrencyService.delete(currency);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void addBalanceTest() {
        try {
            user = new User("Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency);

            wallet = new Wallet(user,currency);
            WalletService.save(wallet);
            id = wallet.getId();

            WalletService.addBalance(wallet, 0.3);

            assertTrue(WalletService.findAllByUser(user)
                    .stream()
                    .anyMatch( w -> w.getId() == id && Math.abs(w.getBalance() - 0.3) < 0.000001));

            WalletService.delete(wallet);
            CurrencyService.delete(currency);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void reduceBalanceTest() {
        try {
            user = new User("Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency);

            wallet = new Wallet(user,currency);
            WalletService.save(wallet);
            id = wallet.getId();

            WalletService.addBalance(wallet, 0.3);
            WalletService.reduceBalance(wallet, 0.2);

            assertTrue(WalletService.findAllByUser(user)
                    .stream()
                    .anyMatch( w -> w.getId() == id && Math.abs(w.getBalance() - 0.1) < 0.000001));

            WalletService.delete(wallet);
            CurrencyService.delete(currency);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user = new User("Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency);

            wallet = new Wallet(user,currency);
            WalletService.save(wallet);
            id = wallet.getId();

            WalletService.addBalance(wallet, 0.3);
            WalletService.reduceBalance(wallet, 0.4);

            fail();
        } catch (Exception e) {
            WalletService.delete(wallet);
            CurrencyService.delete(currency);
            UserService.delete(user);
        }
    }

    @Test
    void transferMoneyTest() {
        try {
            user = new User("Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            WalletService.findAll()
                    .stream()
                    .filter( w->w.getCurrency().getName().contains("TST"))
                    .forEach( w -> WalletService.delete(w) );

            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency);

            wallet = new Wallet(user,currency);
            wallet.setBalance(0.031);
            WalletService.save(wallet);
            id = wallet.getId();

            Wallet wallet2 = new Wallet(user,currency);
            wallet2.setBalance(0.02);
            WalletService.save(wallet2);
            int id2 =wallet2.getId();

            WalletService.transferMoney(wallet,wallet2,0.03);

            assertTrue(WalletService.findAllByUser(user)
                    .stream()
                    .anyMatch( w-> (w.getId()==id && Math.abs(w.getBalance()-0.001)< 0.01 ) ));

            assertTrue(WalletService.findAllByUser(user)
                    .stream()
                    .anyMatch( w-> (w.getId()==id2 && Math.abs(w.getBalance()-0.05) < 0.01) ));

            WalletService.delete(wallet);
            WalletService.delete(wallet2);
            CurrencyService.delete(currency);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user = new User("Test");
            UserService.save(user);

            currency = new Currency("TST", 0.01);
            WalletService.findAll()
                    .stream()
                    .filter( w->w.getCurrency().getName().contains("TST"))
                    .forEach( w -> WalletService.delete(w) );

            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency);

            wallet = new Wallet(user,currency);
            wallet.setBalance(0.031);
            WalletService.save(wallet);
            id = wallet.getId();

            User user2 = new User("Test2");
            UserService.save(user2);

            Currency currency2 = new Currency("TST2", 0.02);
            currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("TST2") )
                    .findAny();
            currencyOpt.ifPresent( c -> CurrencyService.delete(c));
            CurrencyService.save(currency2);

            Wallet wallet2 = new Wallet(user2,currency2);
            wallet2.setBalance(0.02);
            WalletService.save(wallet2);
            int id2 =wallet2.getId();

            WalletService.transferMoney(wallet,wallet2,0.03);

            assertTrue(WalletService.findAllByUser(user)
                    .stream()
                    .anyMatch( w-> (w.getId()==id && Math.abs(w.getBalance()-0.001)< 0.01) ));

            assertTrue(WalletService.findAllByUser(user2)
                    .stream()
                    .anyMatch( w-> (w.getId()==id2 && Math.abs(w.getBalance()-0.035) < 0.01) ));

            WalletService.delete(wallet);
            WalletService.delete(wallet2);
            CurrencyService.delete(currency);
            CurrencyService.delete(currency2);
            UserService.delete(user);
            UserService.delete(user2);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            user = new User("Test");
            UserService.save(user);

            Optional<Currency> currencyOpt = CurrencyService.findAll().stream()
                    .filter( c -> c.getName().equals("BYN") )
                    .findAny();

            wallet = new Wallet(user,currencyOpt.get());
            wallet.setBalance(0.031);
            WalletService.save(wallet);
            id = wallet.getId();

            Wallet wallet2 = new Wallet(user,currencyOpt.get());
            wallet2.setBalance(0.02);
            WalletService.save(wallet2);
            int id2 =wallet2.getId();

            WalletService.transferMoney(wallet,wallet2,0.03);

            assertTrue(WalletService.findAllByUser(user)
                    .stream()
                    .anyMatch( w-> (w.getId()==id && Math.abs(w.getBalance()-0.001)<0.01) ));

            assertTrue(WalletService.findAllByUser(user)
                    .stream()
                    .anyMatch( w-> (w.getId()==id2 && Math.abs(w.getBalance()-0.05) <0.01) ));

            WalletService.delete(wallet);
            WalletService.delete(wallet2);
            UserService.delete(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
