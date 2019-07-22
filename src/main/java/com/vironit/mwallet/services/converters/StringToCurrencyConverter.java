package com.vironit.mwallet.services.converters;

import com.vironit.mwallet.models.dto.CurrencyDto;
import com.vironit.mwallet.services.CurrencyService;
import com.vironit.mwallet.services.mapper.CurrencyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class StringToCurrencyConverter implements Converter<String, CurrencyDto> {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyMapper currencyMapper;

    public CurrencyDto convert(String source) {
        return currencyMapper.toDto(currencyService.findByName(source));
    }
}
