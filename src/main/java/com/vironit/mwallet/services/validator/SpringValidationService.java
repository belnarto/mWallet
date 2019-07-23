package com.vironit.mwallet.services.validator;

import com.vironit.mwallet.models.dto.CurrencyDto;
import com.vironit.mwallet.models.dto.RoleDto;
import com.vironit.mwallet.models.dto.UserDto;
import com.vironit.mwallet.models.dto.WalletDto;
import com.vironit.mwallet.models.entity.Currency;
import com.vironit.mwallet.models.entity.Role;
import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.models.entity.Wallet;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class SpringValidationService implements org.springframework.validation.Validator {

    private Validator validator = getJavaxValidator();

    // getting javax.validation validator
    // which uses validation annotations
    // in entities classes
    @Bean
    public Validator getJavaxValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        return validatorFactory.usingContext().getValidator();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        List<Class> supportedClasses = Arrays.asList(
                User.class,
                UserDto.class,
                Wallet.class,
                WalletDto.class,
                Currency.class,
                CurrencyDto.class,
                Role.class,
                RoleDto.class);
        return supportedClasses.contains(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Set<ConstraintViolation<Object>> violations = validator.validate(target);

        for (ConstraintViolation<Object> constraintViolation : violations) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.rejectValue(propertyPath, "", message);
        }
    }

}