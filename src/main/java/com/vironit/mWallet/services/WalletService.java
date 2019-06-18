package com.vironit.mWallet.services;

import com.vironit.mWallet.dao.WalletDao;
import com.vironit.mWallet.models.User;
import com.vironit.mWallet.models.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletDao walletDao;

    public Wallet findById(int id) {
        return walletDao.findById(id);
    }

    public List<Wallet> findAllByUser(User user) {
        return walletDao.findAllByUser(user);
    }

    public List<Wallet> findAll() {
        return walletDao.findAll();
    }

    public void save(Wallet wallet) {
        walletDao.save(wallet);
    }

    public void delete(Wallet wallet) {
        walletDao.delete(wallet);
    }

    public void update(Wallet wallet) {
        walletDao.update(wallet);
    }

    public void addBalance(Wallet wallet, double value) {
        if (value > 0) {
            wallet.setBalance(wallet.getBalance() + value);
            walletDao.update(wallet);
        } else {
            throw new IllegalArgumentException("Value <= 0");
        }
    }

    public void reduceBalance(Wallet wallet, double value) {
        if (value > 0 && wallet.getBalance() >= value) {
            wallet.setBalance(wallet.getBalance() - value);
            walletDao.update(wallet);
        } else {
            throw new IllegalArgumentException("Value <= 0 or balance is not enough");
        }
    }

    public void transferMoney(Wallet fromWallet, Wallet toWallet, double value) {
        if (value > 0 && fromWallet.getBalance() >= value) {
            fromWallet.setBalance(fromWallet.getBalance() - value);
            walletDao.update(fromWallet);

            double targetValue = fromWallet.getCurrency().getRate() * value; // to BYN
            targetValue = targetValue / toWallet.getCurrency().getRate(); // to target Currency

            targetValue = new BigDecimal(targetValue).setScale(3, RoundingMode.DOWN).doubleValue();

            toWallet.setBalance(toWallet.getBalance() + targetValue);
            walletDao.update(toWallet);
        } else {
            throw new IllegalArgumentException("Value <= 0 or balance is not enough");
        }
    }
}
