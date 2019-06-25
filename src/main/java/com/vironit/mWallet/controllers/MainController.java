package com.vironit.mWallet.controllers;

import com.vironit.mWallet.models.Role;
import com.vironit.mWallet.models.RoleEnum;
import com.vironit.mWallet.models.User;
import com.vironit.mWallet.services.RoleService;
import com.vironit.mWallet.services.SecurityService;
import com.vironit.mWallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private SecurityService securityService;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private UserService userService;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@ModelAttribute("error") String error,
                              @ModelAttribute("logout") String logout) {
        ModelAndView modelAndView = new ModelAndView();
        if (error != null && !error.isEmpty())
            modelAndView.addObject("error", "Your username and password is invalid.");

        if (logout != null && !logout.isEmpty())
            modelAndView.addObject("logout", "You have been logged out successfully.");

        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registrationPage(@ModelAttribute("message") String message) {
        List<Role> roles = roleService.findAll().stream()
                .filter( r -> !r.getRoleEnum().equals(RoleEnum.TST))
                .collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userForm", User.builder().build());
        modelAndView.addObject("roles", roles);
        if (message != null && !message.isEmpty()) {
            modelAndView.addObject("message", "Passwords are not same, try again.");
        }
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("userForm") User userForm,
                                BindingResult bindingResult) {
        userForm.setRole(roleService.findByName(userForm.getRole().getRoleEnum().toString()));
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
            return modelAndView;
        }
//        if (userForm.getPassword().equals(userForm.getPasswordConfirm())) {
//            modelAndView.setViewName("redirect:/main");
//            userService.save(userForm);
//            securityService.autoLogin(userForm.getLogin(), userForm.getPasswordConfirm());
//        } else {
//            modelAndView.setViewName("redirect:/registration?message=passNotSame");
//        }
        return modelAndView;
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied(Principal user) {
        ModelAndView model = new ModelAndView();

        if (user != null) {
            model.addObject("msg", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            model.addObject("msg",
                    "You do not have permission to access this page!");
        }

        model.setViewName("403");
        return model;
    }
}
