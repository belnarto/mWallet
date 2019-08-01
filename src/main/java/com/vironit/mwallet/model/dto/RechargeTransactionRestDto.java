package com.vironit.mwallet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class RechargeTransactionRestDto extends TransactionRestDto {

    @PositiveOrZero(message = "Can't be negative")
    private int walletId;

    @PositiveOrZero(message = "Can't be negative")
    private double amount;

}
