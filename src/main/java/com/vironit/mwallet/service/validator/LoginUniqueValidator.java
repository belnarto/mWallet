package com.vironit.mwallet.service.validator;

import com.vironit.mwallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class LoginUniqueValidator implements ConstraintValidator<LoginUnique, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            return  userService.findByLogin(value) == null;
        } else {
            return true;
        }
    }

}
