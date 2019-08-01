package com.vironit.mwallet.service.impl;

import com.vironit.mwallet.config.WebConfig;
import com.vironit.mwallet.model.attribute.TransactionStatus;
import com.vironit.mwallet.model.dto.RechargeTransactionRestDto;
import com.vironit.mwallet.model.dto.TransactionRestDto;
import com.vironit.mwallet.model.entity.RechargeTransaction;
import com.vironit.mwallet.model.entity.Transaction;
import com.vironit.mwallet.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class TransactionServiceImplTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void findById() {
    }

    @Test
    public void save() {
//        Transaction recharge = new RechargeTransaction(3, 1);
//        recharge.setStatus(TransactionStatus.STARTED);
//        transactionService.save(recharge);
//        recharge.setUpdatedAt(LocalDateTime.now());
//        transactionService.update(recharge);
//        Transaction rechargeSame = transactionService.findById(recharge.getId());
//        transactionService.delete(recharge);
//        List<Transaction> transactionList = transactionService.findAll();

//        ModelMapper modelMapper = new ModelMapper();

//        TypeMap<TransactionRestDto, Transaction> typeMap = modelMapper
//                .createTypeMap(TransactionRestDto.class, Transaction.class)
//                .include(RechargeTransactionRestDto.class, RechargeTransaction.class);
//
//        modelMapper.typeMap(RechargeTransactionRestDto.class, Transaction.class)
//                .setProvider(new Provider<Transaction>() {
//            public Transaction get(ProvisionRequest<Transaction> request) {
//                Object source = request.getSource();
//                RechargeTransactionRestDto source2 = (RechargeTransactionRestDto) source;
//                return new RechargeTransaction(source2.getWalletId(),source2.getAmount());
//            }
//        });
//
//        TransactionRestDto transactionRestDto = new RechargeTransactionRestDto(1,3);
//        transactionRestDto.setId(2);
//        Transaction transaction = modelMapper.map(transactionRestDto, Transaction.class);
//
//        TypeMap<Transaction, TransactionRestDto> typeMap2 = modelMapper
//                .createTypeMap(Transaction.class, TransactionRestDto.class)
//                .include(RechargeTransaction.class, RechargeTransactionRestDto.class);
//
//        modelMapper.typeMap(RechargeTransaction.class, TransactionRestDto.class)
//                .setProvider(new Provider<TransactionRestDto>() {
//                    public TransactionRestDto get(ProvisionRequest<TransactionRestDto> request) {
//                        Object source = request.getSource();
//                        RechargeTransaction source2 = (RechargeTransaction) source;
//                        return new RechargeTransactionRestDto(source2.getWalletId(),source2.getAmount());
//                    }
//                });
//
//        TransactionRestDto transactionRestDto2 = modelMapper.map(transaction, TransactionRestDto.class);
    }

    @Test
    public void update() {



    }

    @Test
    public void findAll() {
    }
}