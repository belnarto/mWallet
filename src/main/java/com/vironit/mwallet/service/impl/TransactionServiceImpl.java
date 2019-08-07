package com.vironit.mwallet.service.impl;

import com.vironit.mwallet.dao.TransactionDao;
import com.vironit.mwallet.model.attribute.TransactionStatus;
import com.vironit.mwallet.model.entity.MoneyTransferTransaction;
import com.vironit.mwallet.model.entity.PaymentTransaction;
import com.vironit.mwallet.model.entity.RechargeTransaction;
import com.vironit.mwallet.model.entity.Transaction;
import com.vironit.mwallet.service.TransactionService;
import com.vironit.mwallet.service.WalletService;
import com.vironit.mwallet.service.exception.TransactionServiceException;
import com.vironit.mwallet.service.exception.WalletServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
@Log4j2
@Transactional(value = "hibernateTransactionManager")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private WalletService walletService;

    @Override
    public Transaction findById(int id) {
        return transactionDao.findById(id);
    }

    @Override
    public void save(Transaction transaction) {
        transactionDao.save(transaction);
        try {
            executeTransaction(transaction);
        } catch (TransactionServiceException e) {
            log.error("Transaction was not saved.", e);
        }
        transaction.setStatus(TransactionStatus.FINISHED);
        update(transaction);
    }

    @Override
    public void delete(Transaction transaction) {
        transactionDao.delete(transaction);
    }

    @Override
    public void update(Transaction transaction) {
        transactionDao.update(transaction);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionDao.findAll();
    }

    // TODO refactor with State pattern
    private void executeTransaction(Transaction transaction)
            throws TransactionServiceException {
        try {
            if (transaction instanceof RechargeTransaction) {
                RechargeTransaction rechargeTransaction = (RechargeTransaction) transaction;
                walletService.addBalance(walletService.findById(rechargeTransaction.getWalletId()),
                        rechargeTransaction.getAmount());
            } else if (transaction instanceof PaymentTransaction) {
                PaymentTransaction paymentTransaction = (PaymentTransaction) transaction;
                walletService.reduceBalance(walletService.findById(paymentTransaction.getWalletId()),
                        paymentTransaction.getAmount());
            } else if (transaction instanceof MoneyTransferTransaction) {
                MoneyTransferTransaction moneyTransferTransaction = (MoneyTransferTransaction) transaction;
                walletService.transferMoney(walletService.findById(moneyTransferTransaction.getFromWalletId()),
                        walletService.findById(moneyTransferTransaction.getToWalletId()),
                        moneyTransferTransaction.getAmount());
            } else {
                log.error("Error during transaction executing");
                throw new TransactionServiceException("Error during transaction executing");
            }
        } catch (WalletServiceException e) {
            log.error("Error during transaction executing", e);
            throw new TransactionServiceException("Error during transaction executing", e);
        }
    }
}
