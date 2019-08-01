package com.vironit.mwallet.model.dto;

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
public class PaymentTransactionRestDto extends TransactionRestDto {

    @NotNull(message = "Can't be null")
    @WalletIdExists
    private String walletId;

    @Positive(message = "Can't be negative or zero")
    private double amount;

    @Positive(message = "Can't be negative or zero")
    private int accountId;
}
