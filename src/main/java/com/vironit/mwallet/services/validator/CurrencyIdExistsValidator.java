package com.vironit.mwallet.services.validator;

import com.vironit.mwallet.services.CurrencyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
@Log4j2
public class CurrencyIdExistsValidator implements ConstraintValidator<CurrencyIdExists, String> {

    @Autowired
    private CurrencyService currencyService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        int currencyId;
        try {
            currencyId = Integer.parseInt(value);
        } catch (RuntimeException e) {
            log.debug("Error during validation", e);
            return false;
        }
        return currencyService.findById(currencyId) != null;
    }

}
