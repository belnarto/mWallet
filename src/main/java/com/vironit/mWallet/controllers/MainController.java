package com.vironit.mWallet.controllers;

import com.vironit.mWallet.models.User;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.vironit.mWallet.services.UserService;

//@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        return modelAndView;
    }

    @RequestMapping(value = "/registry", method = RequestMethod.GET)
    public ModelAndView registryPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registry");
        return modelAndView;
    }

    @RequestMapping(value = "/registry", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login");
        UserService.save(user);
        return modelAndView;
    }

}
