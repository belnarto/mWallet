package com.vironit.mwallet.controller.rest;

import com.vironit.mwallet.controller.rest.exception.*;
import com.vironit.mwallet.service.exception.AuthServiceException;
import com.vironit.mwallet.util.ErrorTransformator;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unused", "unchecked"})
@Log4j2
@ControllerAdvice(annotations = org.springframework.web.bind.annotation.RestController.class)
@Order(1)
public class ExceptionRestController {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleError400(HttpServletRequest request,
                                         Exception e) {
        ResponseEntity responseEntity;
        responseEntity = new ResponseEntity(e.getMessage().replaceAll("nested exception[\\W\\w]+", ""),
                HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleTypeMismatchError(HttpServletRequest request,
                                                  Exception e) {
        ResponseEntity responseEntity;
        responseEntity = new ResponseEntity(e.getMessage(),
                HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity accessDenied(HttpServletRequest request) {
        return new ResponseEntity("Access denied",
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthServiceException.class)
    public ResponseEntity loginFailure(HttpServletRequest request) {
        return new ResponseEntity("Invalid username/password supplied",
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({UserValidationErrorException.class,
            WalletValidationErrorException.class,
            CurrencyValidationErrorException.class,
            TransactionValidationErrorException.class})
    public ResponseEntity<List<String>> validationError(HttpServletRequest request,
                                                 ValidationException e) {
        List<String> errors = ErrorTransformator.transformErrors(e.getObjectErrors());
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, UserRestControllerException.class,
            WalletRestControllerException.class,
            CurrencyRestControllerException.class,
            TransactionRestControllerException.class})
    public ResponseEntity<List<String>> serverError(HttpServletRequest request,
                                                    Exception e) {
        log.error("Error in REST occurred. ", e);
        return new ResponseEntity(Collections.singletonList("Server internal error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<List<String>> notFound(HttpServletRequest request) {
        return new ResponseEntity(Collections.singletonList("Resource not found"),
                HttpStatus.NOT_FOUND);
    }
}
