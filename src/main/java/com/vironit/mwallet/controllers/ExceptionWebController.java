package com.vironit.mwallet.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDeniedException;

import java.security.Principal;

@SuppressWarnings("unused")
@Log4j2
@ControllerAdvice
@Order(2)
public class ExceptionWebController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest request,
                                    Exception e) {
        log.error("Error occurred: " + e.getMessage() + "\n"
                + "Request URI: " + request.getRequestURI() + "\n"
                + "Stack trace: " + e);
        ModelAndView modelAndView = new ModelAndView("/errorPage");
        modelAndView.addObject("errorTitle", "Error occurred.");
        modelAndView.addObject("errorMsg", "Sorry, error occurred with " +
                "your request. Our engineers are working on it.");
        return modelAndView;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleError404(HttpServletRequest request,
                                       NoHandlerFoundException e) {
        ModelAndView modelAndView = new ModelAndView("/errorPage");
        modelAndView.addObject("errorTitle", "Page not found.");
        modelAndView.addObject("errorMsg", "Sorry, but page you are looking " +
                "for is not found.");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView accessDeniedPage(ModelAndView modelAndView,
                                         Principal user) {

        modelAndView.addObject("errorTitle", "Access is denied.");

        if (user != null) {
            modelAndView.addObject("errorMsg", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            modelAndView.addObject("errorMsg",
                    "You do not have permission to access this page!");
        }

        modelAndView.setViewName("errorPage");
        return modelAndView;
    }

}
