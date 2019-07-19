package com.vironit.mwallet.models.dto;

import com.vironit.mwallet.models.attributes.WalletStatusEnum;
import com.vironit.mwallet.models.entity.Currency;
import com.vironit.mwallet.models.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WalletDto {

    private int id;
    private User user;
    private Currency currency;
    private double balance;
    private WalletStatusEnum walletStatus;

}
