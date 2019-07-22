package com.vironit.mwallet.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Apply globally to all Controllers
 * by annotation @ControllerAdvice
 */
@Log4j2
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest request,
                                    Exception e) {
        log.error("Error occurred: " + e.getMessage() + "\n"
                + "Request: " + request + "\n"
                + "Stack trace: " + e);
        ModelAndView modelAndView = new ModelAndView("/errorPage");
        modelAndView.addObject("errorTitle", "Error occurred.");
        modelAndView.addObject("errorMsg", "Sorry, error occurred with " +
                "your request. Our engineers are working on it.");
        return modelAndView;
    }

    @SuppressWarnings("unused")
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleError404(HttpServletRequest request,
                                       Exception e) {
        ModelAndView modelAndView = new ModelAndView("/errorPage");
        modelAndView.addObject("errorTitle", "Page not found.");
        modelAndView.addObject("errorMsg", "Sorry, but page you are looking " +
                "for is not found.");
        return modelAndView;
    }

}
