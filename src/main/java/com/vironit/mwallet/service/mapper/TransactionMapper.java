package com.vironit.mwallet.service.mapper;

import com.vironit.mwallet.model.dto.MoneyTransferTransactionRestDto;
import com.vironit.mwallet.model.dto.PaymentTransactionRestDto;
import com.vironit.mwallet.model.dto.RechargeTransactionRestDto;
import com.vironit.mwallet.model.dto.TransactionRestDto;
import com.vironit.mwallet.model.entity.MoneyTransferTransaction;
import com.vironit.mwallet.model.entity.PaymentTransaction;
import com.vironit.mwallet.model.entity.RechargeTransaction;
import com.vironit.mwallet.model.entity.Transaction;
import com.vironit.mwallet.service.CurrencyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class TransactionMapper {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CurrencyService currencyService;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(TransactionRestDto.class, Transaction.class)
                .include(RechargeTransactionRestDto.class, RechargeTransaction.class)
                .include(PaymentTransactionRestDto.class, PaymentTransaction.class)
                .include(MoneyTransferTransactionRestDto.class, MoneyTransferTransaction.class)
                ;

        mapper.createTypeMap(Transaction.class, TransactionRestDto.class)
                .include(RechargeTransaction.class, RechargeTransactionRestDto.class)
                .include(PaymentTransaction.class, PaymentTransactionRestDto.class)
                .include(MoneyTransferTransaction.class, MoneyTransferTransactionRestDto.class);


        mapper.typeMap(RechargeTransactionRestDto.class, Transaction.class)
                .setProvider(request -> {
                    RechargeTransactionRestDto source =
                            (RechargeTransactionRestDto) request.getSource();
                    return new RechargeTransaction(Integer.parseInt(source.getWalletId()),
                            source.getAmount());
                });

        mapper.typeMap(RechargeTransaction.class, TransactionRestDto.class)
                .setProvider(request -> {
                    RechargeTransaction source =
                            (RechargeTransaction) request.getSource();
                    return new RechargeTransactionRestDto(String.valueOf(source.getWalletId()),
                            source.getAmount());
                });


        mapper.typeMap(PaymentTransactionRestDto.class, Transaction.class)
                .setProvider(request -> {
                    PaymentTransactionRestDto source =
                            (PaymentTransactionRestDto) request.getSource();
                    return new PaymentTransaction(Integer.parseInt(source.getWalletId()),
                            source.getAmount(),
                            source.getAccountId());
                });

        mapper.typeMap(PaymentTransaction.class, TransactionRestDto.class)
                .setProvider(request -> {
                    PaymentTransaction source =
                            (PaymentTransaction) request.getSource();
                    return new PaymentTransactionRestDto(String.valueOf(source.getWalletId()),
                            source.getAmount(),
                            source.getAccountId());
                });


        mapper.typeMap(MoneyTransferTransactionRestDto.class, Transaction.class)
                .setProvider(request -> {
                    MoneyTransferTransactionRestDto source =
                            (MoneyTransferTransactionRestDto) request.getSource();
                    return new MoneyTransferTransaction(Integer.parseInt(source.getFromWalletId()),
                            Integer.parseInt(source.getToWalletId()),
                            source.getAmount(),
                            currencyService.findById(Integer.parseInt(source.getCurrencyId()))
                            );
                });

        mapper.typeMap(MoneyTransferTransaction.class, TransactionRestDto.class)
                .setProvider(request -> {
                    MoneyTransferTransaction source =
                            (MoneyTransferTransaction) request.getSource();
                    return new MoneyTransferTransactionRestDto(String.valueOf(source.getFromWalletId()),
                            String.valueOf(source.getToWalletId()),
                            source.getAmount(),
                            String.valueOf(source.getCurrency().getId()));
                });
    }

    public Transaction toEntity(TransactionRestDto transactionRestDto) {
        return Objects.isNull(transactionRestDto)
                ? null
                : mapper.map(transactionRestDto, Transaction.class);
    }

    public TransactionRestDto toDto(Transaction transaction) {
        return Objects.isNull(transaction)
                ? null
                : mapper.map(transaction, TransactionRestDto.class);
    }


}