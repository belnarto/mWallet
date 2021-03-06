package com.vironit.mwallet.model.dto;

import com.vironit.mwallet.service.validator.WalletIdExists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class RechargeTransactionRestDto extends TransactionRestDto {

    @NotNull(message = "Can't be null")
    @WalletIdExists
    private String walletId;

    @PositiveOrZero(message = "Can't be negative")
    private double amount;

}
