package com.vironit.mwallet.controller.mvc;

import com.vironit.mwallet.service.RoleService;
import com.vironit.mwallet.service.UserService;
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

    @GetMapping(value = {"/", "main"})
    public ModelAndView rootPage(ModelAndView modelAndView,
                                 Principal principal) {
        modelAndView.setViewName("main");

        if (principal != null) {
            modelAndView.addObject("userId", userService.findByLogin(principal.getName()).getId());
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
                                         Principal principal) {

        modelAndView.addObject("errorTitle", "Access is denied.");

        if (principal != null) {
            modelAndView.addObject("errorMsg", "Hi " + principal.getName()
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
                                     Principal principal) {
        return accessDeniedPage(modelAndView, principal);
    }
}
