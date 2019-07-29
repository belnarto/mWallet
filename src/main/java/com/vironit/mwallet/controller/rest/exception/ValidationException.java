package com.vironit.mwallet.controller.rest.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

public interface ValidationException {

    List<ObjectError> getObjectErrors();
}
