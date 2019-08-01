package com.vironit.mwallet.model.dto;

import com.vironit.mwallet.service.validator.CurrencyIdExists;
import com.vironit.mwallet.service.validator.WalletIdExists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class MoneyTransferTransactionRestDto extends TransactionRestDto {

    @NotNull(message = "Can't be null")
    @WalletIdExists
    private String fromWalletId;

    @NotNull(message = "Can't be null")
    @WalletIdExists
    private String toWalletId;

    @Positive(message = "Can't be negative or zero")
    private double amount;

    @NotNull(message = "Can't be null")
    @CurrencyIdExists
    private String currencyId;
}
