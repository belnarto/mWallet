package com.vironit.mwallet.service.converter;

import com.vironit.mwallet.model.dto.CurrencyDto;
import com.vironit.mwallet.service.CurrencyService;
import com.vironit.mwallet.service.mapper.CurrencyMapper;
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
