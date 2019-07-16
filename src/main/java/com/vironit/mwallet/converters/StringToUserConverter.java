package com.vironit.mwallet.converters;

import com.vironit.mwallet.models.Currency;
import com.vironit.mwallet.models.User;
import com.vironit.mwallet.services.CurrencyService;
import com.vironit.mwallet.services.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUserConverter implements Converter<String, User> {

    private UserService userService;

    public StringToUserConverter(UserService userService) {
        this.userService = userService;
    }

    public User convert(String source) {
        return userService.findById(Integer.parseInt(source));
    }
}
