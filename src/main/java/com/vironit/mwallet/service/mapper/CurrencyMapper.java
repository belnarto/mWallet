package com.vironit.mwallet.service.mapper;

import com.vironit.mwallet.model.dto.CurrencyDto;
import com.vironit.mwallet.model.entity.Currency;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class CurrencyMapper {

    @Autowired
    private ModelMapper mapper;

    public Currency toEntity(CurrencyDto currencyDto) {
        return Objects.isNull(currencyDto) ? null : mapper.map(currencyDto, Currency.class);
    }

    public CurrencyDto toDto(Currency currencyEntity) {
        return Objects.isNull(currencyEntity) ? null : mapper.map(currencyEntity, CurrencyDto.class);
    }
}