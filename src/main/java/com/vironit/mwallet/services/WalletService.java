package com.vironit.mwallet.services;

import com.vironit.mwallet.models.dto.UserDto;
import com.vironit.mwallet.models.dto.WalletDto;
import com.vironit.mwallet.services.exception.WalletServiceException;

import java.util.List;

public interface WalletService {

    WalletDto findById(int id);

    List<WalletDto> findAllByUser(UserDto userDto);

    List<WalletDto> findAll();

    void save(WalletDto walletDto) throws WalletServiceException;

    void delete(WalletDto walletDto);

    void update(WalletDto walletDto);

    void addBalance(WalletDto walletDto, double value)
     throws WalletServiceException;

    void reduceBalance(WalletDto walletDto, double value)
     throws WalletServiceException;

    void transferMoney(WalletDto fromWalletDto, WalletDto toWalletDto, double value)
            throws WalletServiceException;

}
