package com.vironit.mwallet.model.dto;

import com.vironit.mwallet.service.validator.CurrencyIdExists;
import com.vironit.mwallet.service.validator.UserIdExists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WalletRestDtoWithUserAndCurrencyId {

    @PositiveOrZero(message = "Can't be negative")
    private int id;

    @UserIdExists
    private String userId;

    @CurrencyIdExists
    private String currencyId;

}
