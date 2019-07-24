package com.vironit.mwallet.services.validator;

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
@Constraint(validatedBy = UserIdExistsValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface UserIdExists {

    String message() default "There is no user with such id.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};

}
