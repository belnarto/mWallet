package com.vironit.mwallet.service.mapper;

import com.vironit.mwallet.model.dto.RechargeTransactionRestDto;
import com.vironit.mwallet.model.dto.TransactionRestDto;
import com.vironit.mwallet.model.entity.RechargeTransaction;
import com.vironit.mwallet.model.entity.Transaction;
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

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(TransactionRestDto.class, Transaction.class)
                .include(RechargeTransactionRestDto.class, RechargeTransaction.class);

        mapper.createTypeMap(Transaction.class, TransactionRestDto.class)
                .include(RechargeTransaction.class, RechargeTransactionRestDto.class);

        mapper.typeMap(RechargeTransactionRestDto.class, Transaction.class)
                .setProvider(request -> {
                    Object source = request.getSource();
                    RechargeTransactionRestDto source2 = (RechargeTransactionRestDto) source;
                    return new RechargeTransaction(source2.getWalletId(), source2.getAmount());
                });

        mapper.typeMap(RechargeTransaction.class, TransactionRestDto.class)
                .setProvider(request -> {
                    Object source = request.getSource();
                    RechargeTransaction source2 = (RechargeTransaction) source;
                    return new RechargeTransactionRestDto(source2.getWalletId(), source2.getAmount());
                });
    }

    public Transaction toEntity(TransactionRestDto transactionRestDto) {
        return Objects.isNull(transactionRestDto) ? null : mapper.map(transactionRestDto, Transaction.class);
    }

    public TransactionRestDto toDto(Transaction transaction) {
        return Objects.isNull(transaction) ? null : mapper.map(transaction, TransactionRestDto.class);
    }


}