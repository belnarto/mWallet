package com.vironit.mwallet.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings({"unused", "unchecked"})
@Log4j2
@ControllerAdvice(annotations = org.springframework.web.bind.annotation.RestController.class)
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

}
