package com.vironit.mwallet.services.validator;

import com.vironit.mwallet.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class RoleIdExistsValidator implements ConstraintValidator<RoleIdExists, String> {

    @Autowired
    private RoleService roleService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && roleService.findById(Integer.parseInt(value)) != null;
    }

}
