package com.vironit.mwallet.controllers;

import com.vironit.mwallet.services.RoleService;
import com.vironit.mwallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/")
    public ModelAndView rootPage(ModelAndView modelAndView,
                                 Principal user) {
        modelAndView.setViewName("main");

        if (user != null) {
            modelAndView.addObject("userId", userService.findByLogin(user.getName()).getId());
        }

        return modelAndView;
    }

    @GetMapping(value = "/main")
    public ModelAndView mainPage(ModelAndView modelAndView,
                                 Principal user) {
        modelAndView.setViewName("main");

        if (user != null) {
            modelAndView.addObject("userId", userService.findByLogin(user.getName()).getId());
        }

        return modelAndView;
    }

    @GetMapping(value = "/login")
    public ModelAndView loginPage(ModelAndView modelAndView,
                                  @ModelAttribute("error") String error,
                                  @ModelAttribute("logout") String logout) {

        if (error != null && !error.isEmpty())
            modelAndView.addObject("error", "Your username and password are invalid.");

        if (logout != null && !logout.isEmpty())
            modelAndView.addObject("logout", "You have been logged out successfully.");

        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value = "/403")
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

    @PostMapping(value = "/403")
    public ModelAndView accessDenied(ModelAndView modelAndView,
                                     Principal user) {
        return accessDeniedPage(modelAndView, user);
    }
}
