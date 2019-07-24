package com.vironit.mwallet.services.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@SuppressWarnings("unused")
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {UniqueLoginValidator.class})
@Documented
public @interface UniqueLogin {

    String message() default "This login is already occupied.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
