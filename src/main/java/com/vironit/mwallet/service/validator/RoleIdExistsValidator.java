package com.vironit.mwallet.service.validator;

import com.vironit.mwallet.service.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
@Log4j2
public class RoleIdExistsValidator implements ConstraintValidator<RoleIdExists, String> {

    @Autowired
    private RoleService roleService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        int roleId;
        try {
            roleId = Integer.parseInt(value);
        } catch (RuntimeException e) {
            log.debug("Error during validation", e);
            return false;
        }
        return roleService.findById(roleId) != null;
    }

}
