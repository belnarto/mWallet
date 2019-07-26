package com.vironit.mwallet.utils;

import lombok.experimental.UtilityClass;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * used to transform ObjectError List to String List
 */
@UtilityClass
public class ErrorTransformator {

    public List<String> transformErrors(List<ObjectError> errors) {
        List<String> result = new LinkedList<>();

        result.addAll(errors.stream()
                .filter(objectError -> objectError instanceof FieldError)
                .map(objectError -> (FieldError) objectError)
                .map(fieldError -> "field: " + fieldError.getField() +
                        ", rejected value: " + fieldError.getRejectedValue() +
                        ", message: " + fieldError.getDefaultMessage())
                .collect(Collectors.toList()));

        result.addAll(errors.stream()
                .filter(objectError -> objectError.getClass().equals(ObjectError.class))
                .map(objectError -> "error: " + objectError.getDefaultMessage())
                .collect(Collectors.toList()));

        return result;
    }

}
