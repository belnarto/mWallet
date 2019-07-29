package com.vironit.mwallet.service.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * https://medium.com/@wkrzywiec/how-to-check-if-user-exist-in-database-using-hibernate-validator-eab110429a6
 */
@SuppressWarnings("unused")
@Constraint(validatedBy = LoginUniqueValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface LoginUnique {

    String message() default "This login is already occupied.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};

}
