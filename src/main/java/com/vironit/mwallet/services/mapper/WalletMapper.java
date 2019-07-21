package com.vironit.mwallet.services.mapper;

import com.vironit.mwallet.dao.CurrencyDao;
import com.vironit.mwallet.dao.UserDao;
import com.vironit.mwallet.models.dto.UserDto;
import com.vironit.mwallet.models.dto.WalletDto;
import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.models.entity.Wallet;
import com.vironit.mwallet.services.UserService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class WalletMapper {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CurrencyDao currencyDao;

    public Wallet toEntity(WalletDto walletDto) {
        return Objects.isNull(walletDto) ? null : mapper.map(walletDto, Wallet.class);
    }

    public WalletDto toDto(Wallet walletEntity) {
        return Objects.isNull(walletEntity) ? null : mapper.map(walletEntity, WalletDto.class);
    }

}