package com.vironit.mwallet.models.dto;

import com.vironit.mwallet.models.attributes.WalletStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WalletDto {

    @PositiveOrZero(message = "Can't be negative")
    private int id;

    // Но если мы попробуем таким образом законвертировать что-нибудь,
    // а потом вызвать, к примеру, toString, то мы получим StackOverflowException,
    // и вот почему: в WalletDto находится UserDto, в котором находится лист WalletDto,
    // в котором находятся UserDto, и так до того момента, пока не закончится стековая память.
    // Поэтому для обратных зависимостей используем не UserDto userDto,
    // а int userId. Мы, таким образом, сохраняем связь с User,
    // но обрубаем циклическую зависимость.
    // private UserDto user;
    @PositiveOrZero(message = "Can't be negative")
    private Integer userId;

    @NotNull(message = "Can't be null")
    private CurrencyDto currency;

    @PositiveOrZero(message = "Can't be negative")
    private double balance;

    @NotNull(message = "Can't be null")
    private WalletStatusEnum walletStatus = WalletStatusEnum.ACTIVE;

}
