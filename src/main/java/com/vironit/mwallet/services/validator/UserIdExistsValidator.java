package com.vironit.mwallet.services.validator;

import com.vironit.mwallet.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
@Log4j2
public class UserIdExistsValidator implements ConstraintValidator<UserIdExists, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        int userId;
        try {
            userId = Integer.parseInt(value);
        } catch (RuntimeException e) {
            log.debug("Error during validation", e);
            return false;
        }
        return userService.findById(userId) != null;
    }

}
