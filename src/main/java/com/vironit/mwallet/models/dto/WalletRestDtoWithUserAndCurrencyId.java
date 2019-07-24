package com.vironit.mwallet.models.dto;

import com.vironit.mwallet.services.validator.CurrencyIdExists;
import com.vironit.mwallet.services.validator.UserIdExists;
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
