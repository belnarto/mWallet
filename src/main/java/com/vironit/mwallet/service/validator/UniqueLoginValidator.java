package com.vironit.mwallet.service.validator;

import com.vironit.mwallet.model.dto.UserRestDto;
import com.vironit.mwallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, UserRestDto> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UniqueLogin constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserRestDto userRestDto, ConstraintValidatorContext context) {
        if ( userRestDto == null ) {
            return true;
        }

        int userId = userRestDto.getId();

        String currentLogin = null;
        String newLogin = userRestDto.getLogin();

        if (userId != 0) {
            currentLogin = userService.findById(userId).getLogin();
        }

        if (currentLogin == null) {
            return userService.findByLogin(newLogin) == null;
        } else if (currentLogin.equals(newLogin)) {
            return true;
        } else {
            return userService.findByLogin(newLogin) == null;
        }

    }

}
