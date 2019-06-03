package services;

import dao.WalletDao;
import models.User;
import models.Wallet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class WalletService {

    public WalletService() {}

    public static Wallet findById(int id) {
        return WalletDao.findById(id);
    }

    public static List<Wallet> findAllByUser(User user) {
        return WalletDao.findAllByUser(user);
    }

    public static List<Wallet> findAll() {
        return WalletDao.findAll();
    }

    public static void save(Wallet wallet) {
        WalletDao.save(wallet);
    }

    public static void delete(Wallet wallet) {
        WalletDao.delete(wallet);
    }

    public static void update(Wallet wallet) {
        WalletDao.update(wallet);
    }

    public static void addBalance(Wallet wallet, double value) {
        if(value>0) {
            wallet.setBalance(wallet.getBalance() + value);
            WalletDao.update(wallet);
        } else {
            throw new IllegalArgumentException("Value <= 0");
        }
    }

    public static void reduceBalance(Wallet wallet, double value) {
        if(value>0 && wallet.getBalance()>=value) {
            wallet.setBalance(wallet.getBalance() - value);
            WalletDao.update(wallet);
        } else {
            throw new IllegalArgumentException("Value <= 0 or balance is not enough");
        }
    }

    public static void transferMoney(Wallet fromWallet, Wallet toWallet, double value) {
        if(value>0 && fromWallet.getBalance()>=value) {
            fromWallet.setBalance(fromWallet.getBalance() - value);
            WalletDao.update(fromWallet);

            double targetValue = fromWallet.getCurrency().getRate() * value; // to BYN
            targetValue = targetValue / toWallet.getCurrency().getRate(); // to target Currency

            targetValue = new BigDecimal(targetValue).setScale(3, RoundingMode.DOWN).doubleValue();

            toWallet.setBalance(toWallet.getBalance() + targetValue);
            WalletDao.update(toWallet);
        } else {
            throw new IllegalArgumentException("Value <= 0 or balance is not enough");
        }
    }
}
