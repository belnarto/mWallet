package com.vironit.mwallet.controllers;

import com.vironit.mwallet.services.RoleService;
import com.vironit.mwallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class MainController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private UserService userService;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public ModelAndView mainGet(ModelAndView modelAndView,
                                Principal user) {
        modelAndView.setViewName("main");

        if (user != null) {
            modelAndView.addObject("userId", userService.findByLogin(user.getName()).getId());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginGet(ModelAndView modelAndView,
                                  @ModelAttribute("error") String error,
                                  @ModelAttribute("logout") String logout) {

        if (error != null && !error.isEmpty())
            modelAndView.addObject("error", "Your username and password are invalid.");

        if (logout != null && !logout.isEmpty())
            modelAndView.addObject("logout", "You have been logged out successfully.");

        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDeniedGet(ModelAndView modelAndView,
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
