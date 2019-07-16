package com.vironit.mwallet.converters;

import com.vironit.mwallet.models.Currency;
import com.vironit.mwallet.models.Role;
import com.vironit.mwallet.services.CurrencyService;
import com.vironit.mwallet.services.RoleService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCurrencyConverter implements Converter<String, Currency> {

    private CurrencyService currencyService;

    public StringToCurrencyConverter(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public Currency convert(String source) {
        return currencyService.findByName(source);
    }
}
