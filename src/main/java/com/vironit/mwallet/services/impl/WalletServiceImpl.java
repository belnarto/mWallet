package com.vironit.mwallet.services.impl;

import com.vironit.mwallet.dao.WalletDao;
import com.vironit.mwallet.models.entity.Currency;
import com.vironit.mwallet.services.CurrencyService;
import com.vironit.mwallet.services.WalletService;
import com.vironit.mwallet.services.exception.WalletServiceException;
import com.vironit.mwallet.services.exception.WalletStatusException;
import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.models.entity.Wallet;
import com.vironit.mwallet.models.attributes.WalletStatusEnum;
import com.vironit.mwallet.services.mapper.UserMapper;
import com.vironit.mwallet.services.mapper.WalletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
@Transactional(value = "hibernateTransactionManager")
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletDao walletDao;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CurrencyService currencyService;

    public Wallet findById(int id) {
        return walletDao.findById(id);
    }

    public Set<Wallet> findAllByUser(User user) {
        return new HashSet<>(walletDao.findAllByUser(user));
    }

    public List<Wallet> findAll() {
        return walletDao.findAll();
    }

    public void save(Wallet wallet) throws WalletServiceException {
        if (wallet.getBalance() != 0) {
            throw new WalletServiceException("balance not 0.");
        }
        walletDao.save(wallet);
    }

    public void delete(Wallet wallet) {
        walletDao.delete(wallet);
    }

    public void update(Wallet wallet) {
        Currency currency = walletDao.findById(wallet.getId()).getCurrency();
        if (!currency.equals(wallet.getCurrency())) {
            recalculateBalanceAfterCurrencyChange(wallet, currency);
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
            throw new WalletStatusException("Operation is not permitted because wallet " + toWallet.getId() + " is blocked");
        }
        if (value <= 0) {
            throw new WalletServiceException("Value <= 0");
        }

        if (value < fromWallet.getBalance()) {
            fromWallet.setBalance(fromWallet.getBalance() - value);
            walletDao.update(fromWallet);

            double targetValue = fromWallet.getCurrency().getRate() * value; // to BYN
            targetValue = targetValue / toWallet.getCurrency().getRate(); // to target Currency

            targetValue = BigDecimal.valueOf(targetValue).setScale(3, RoundingMode.DOWN).doubleValue();
            toWallet.setBalance(toWallet.getBalance() + targetValue);

            walletDao.update(toWallet);
        } else {
            throw new WalletServiceException("Wallet has not enough balance");
        }
    }

    private void recalculateBalanceAfterCurrencyChange(
            Wallet wallet, Currency currencyOld) {
        double balance = wallet.getBalance();
        double targetValue = currencyOld.getRate() * balance; // to BYN
        targetValue = targetValue / wallet.getCurrency().getRate(); // to new Currency
        targetValue = BigDecimal.valueOf(targetValue)
                .setScale(3, RoundingMode.DOWN)
                .doubleValue();
        wallet.setBalance(targetValue);
    }
}
