package com.vironit.mwallet.service.validator;

import com.vironit.mwallet.service.WalletService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
@Log4j2
public class WalletIdExistsValidator implements ConstraintValidator<WalletIdExists, String> {

    @Autowired
    private WalletService walletService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        int walletId;
        try {
            walletId = Integer.parseInt(value);
        } catch (RuntimeException e) {
            log.debug("Error during validation", e);
            return false;
        }
        return walletService.findById(walletId) != null;
    }

}
