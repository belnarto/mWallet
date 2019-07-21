package com.vironit.mwallet.services.converters;

import com.vironit.mwallet.models.dto.CurrencyDto;
import com.vironit.mwallet.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class StringToCurrencyConverter implements Converter<String, CurrencyDto> {

    @Autowired
    private CurrencyService currencyService;

    public CurrencyDto convert(String source) {
        return currencyService.findByName(source);
    }
}
