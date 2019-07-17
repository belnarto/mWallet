package com.vironit.mwallet.services.converters;

import com.vironit.mwallet.models.Currency;
import com.vironit.mwallet.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCurrencyConverter implements Converter<String, Currency> {

    @Autowired
    private CurrencyService currencyService;

    public Currency convert(String source) {
        return currencyService.findByName(source);
    }
}
