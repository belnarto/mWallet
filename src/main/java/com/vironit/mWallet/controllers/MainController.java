package com.vironit.mWallet.controllers;

import com.vironit.mWallet.services.RoleService;
import com.vironit.mWallet.services.UserService;
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
    public ModelAndView mainGet(ModelAndView modelAndView) {
        modelAndView.setViewName("main");
        roleService.findAll(); //TODO delete
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

        if (user != null) {
            modelAndView.addObject("msg", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            modelAndView.addObject("msg",
                    "You do not have permission to access this page!");
        }

        modelAndView.setViewName("403");
        return modelAndView;
    }
}
