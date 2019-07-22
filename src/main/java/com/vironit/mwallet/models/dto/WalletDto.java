package com.vironit.mwallet.models.dto;

import com.vironit.mwallet.models.attributes.WalletStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WalletDto {

    private int id;

    // Но если мы попробуем таким образом законвертировать что-нибудь,
    // а потом вызвать, к примеру, toString, то мы получим StackOverflowException,
    // и вот почему: в WalletDto находится UserDto, в котором находится лист WalletDto,
    // в котором находятся UserDto, и так до того момента, пока не закончится стековая память.
    // Поэтому для обратных зависимостей используем не UserDto userDto,
    // а int userId. Мы, таким образом, сохраняем связь с User,
    // но обрубаем циклическую зависимость.
    // private UserDto user;
    private Integer userId;

    private CurrencyDto currency;
    //private int currencyId;

    private double balance;
    private WalletStatusEnum walletStatus;

}
