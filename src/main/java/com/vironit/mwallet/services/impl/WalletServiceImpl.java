package com.vironit.mwallet.services.impl;

import com.vironit.mwallet.dao.WalletDao;
import com.vironit.mwallet.services.WalletService;
import com.vironit.mwallet.services.exception.WalletServiceException;
import com.vironit.mwallet.services.exception.WalletStatusException;
import com.vironit.mwallet.models.Currency;
import com.vironit.mwallet.models.User;
import com.vironit.mwallet.models.Wallet;
import com.vironit.mwallet.models.WalletStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional(value = "hibernateTransactionManager")
public class WalletServiceImpl implements WalletService {

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
        Currency currencyOld = walletDao.findById(wallet.getId()).getCurrency();
        if (!currencyOld.equals(wallet.getCurrency())) {
            recalculateBalanceAfterCurrencyChange(wallet, currencyOld);
        }
        walletDao.update(wallet);
    }

    public void addBalance(Wallet wallet, double value)
            throws WalletServiceException {
        if (wallet.getWalletStatus().equals(WalletStatusEnum.BLOCKED)) {
            throw new WalletStatusException("Operation is not permitted because wallet is blocked");
        }
        if (value > 0) {
            wallet.setBalance(wallet.getBalance() + value);
            walletDao.update(wallet);
        } else {
            throw new WalletServiceException("Value <= 0");
        }
    }

    public void reduceBalance(Wallet wallet, double value)
            throws WalletServiceException {
        if (wallet.getWalletStatus().equals(WalletStatusEnum.BLOCKED)) {
            throw new WalletStatusException("Operation is not permitted because wallet is blocked");
        }
        if (value <= 0) {
            throw new WalletServiceException("Value <= 0");
        }
        if (value < wallet.getBalance()) {
            wallet.setBalance(wallet.getBalance() - value);
            walletDao.update(wallet);
        } else {
            throw new WalletServiceException("Wallet has not enough balance");
        }
    }

    public void transferMoney(Wallet fromWallet, Wallet toWallet, double value)
            throws WalletServiceException {
        if (fromWallet.getWalletStatus().equals(WalletStatusEnum.BLOCKED)) {
            throw new WalletStatusException("Operation is not permitted because wallet " + fromWallet.getId() + " is blocked");
        }
        if (toWallet.getWalletStatus().equals(WalletStatusEnum.BLOCKED)) {
            throw new WalletStatusException("Operation is not permitted because wallet " + toWallet.getId()  + " is blocked");
        }
        if (value <= 0) {
            throw new WalletServiceException("Value <= 0");
        }
        // TODO refactor
        if (value < fromWallet.getBalance()) {
            fromWallet.setBalance(fromWallet.getBalance() - value);
            walletDao.update(fromWallet);

            double targetValue = fromWallet.getCurrency().getRate() * value; // to BYN
            targetValue = targetValue / toWallet.getCurrency().getRate(); // to target Currency

            targetValue = new BigDecimal(targetValue).setScale(3, RoundingMode.DOWN).doubleValue();
            toWallet.setBalance(toWallet.getBalance() + targetValue);

            walletDao.update(toWallet);
        } else {
            throw new WalletServiceException("Wallet has not enough balance");
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void blockWallet(Wallet wallet) {
        wallet.setWalletStatus(WalletStatusEnum.BLOCKED);
        walletDao.update(wallet);
    }

    @SuppressWarnings("WeakerAccess")
    public void activateWallet(Wallet wallet) {
        wallet.setWalletStatus(WalletStatusEnum.ACTIVE);
        walletDao.update(wallet);
    }

    //TODO refactor
    private void recalculateBalanceAfterCurrencyChange(
            Wallet wallet, Currency currencyOld) {
        double balance = wallet.getBalance();
        double targetValue = currencyOld.getRate() * balance; // to BYN
        targetValue = targetValue / wallet.getCurrency().getRate(); // to new Currency
        targetValue = new BigDecimal(targetValue)
                .setScale(3, RoundingMode.DOWN)
                .doubleValue();
        wallet.setBalance(targetValue);
    }
}
