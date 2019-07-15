package com.vironit.mwallet.controllers;

import com.vironit.mwallet.models.Role;
import com.vironit.mwallet.models.User;
import com.vironit.mwallet.services.RoleService;
import com.vironit.mwallet.services.UserService;
import com.vironit.mwallet.services.exception.LoginAlreadyDefinedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@Log4j2
public class UserController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private UserService userService;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private RoleService roleService;

    @Qualifier("mvcValidator")
    @SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "unused"})
    @Autowired
    private Validator validator;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView allUsers(ModelAndView modelAndView) {
        modelAndView.setViewName("userPages/users");
        modelAndView.addObject("users", userService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public ModelAndView myUser(ModelAndView modelAndView,
                                 @PathVariable("userId") int userId) {
        modelAndView.setViewName("userPages/users");
        List<User> myUser = new ArrayList<>();
        myUser.add(userService.findById(userId));
        modelAndView.addObject("users", myUser);
        return modelAndView;
    }

    @RequestMapping(value = "/users/addUser", method = RequestMethod.GET)
    public ModelAndView addUserGet(ModelAndView modelAndView) {
        modelAndView.setViewName("userPages/addUser");
        modelAndView.addObject("roles", roleService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/users/addUser", method = RequestMethod.POST)
    public ModelAndView addUserPost(ModelAndView modelAndView,
                                    @Valid @ModelAttribute("user") User user,
                                    BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            try {
                userService.save(user);
                modelAndView.setViewName("main");
                modelAndView.addObject("added", true);
                return modelAndView;
            } catch (LoginAlreadyDefinedException e) {
                log.debug("login already defined. " + user);
                bindingResult.addError(new FieldError("user", "login", "login already defined."));
            }
        }
        modelAndView.setViewName("userPages/addUser");
        modelAndView.addObject("roles", roleService.findAll());
        modelAndView.addObject("fieldErrors", bindingResult.getFieldErrors());
        return modelAndView;
    }

    @RequestMapping(value = "/users/{userId}/updateUser", method = RequestMethod.GET)
    public ModelAndView updateUserGet(ModelAndView modelAndView,
                                      @PathVariable("userId") int userId) {
        User user = userService.findById(userId);
        List<Role> roles = roleService.findAll();
        roles.remove(user.getRole());
        modelAndView.setViewName("userPages/updateUser");
        modelAndView.addObject("user", user);
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }


    @SuppressWarnings("MVCPathVariableInspection")
    @RequestMapping(value = "/users/{userId}/updateUser", method = RequestMethod.POST)
    public ModelAndView updateUserPost(ModelAndView modelAndView,
                                       @Valid @ModelAttribute("user") User user,
                                       BindingResult bindingResult) {
        List<Role> roles = roleService.findAll();
        roles.remove(user.getRole());
        modelAndView.setViewName("userPages/updateUser");
        modelAndView.addObject("roles", roles);
        if (!bindingResult.hasErrors()) {
            userService.update(user);
            modelAndView.addObject("updated", true);
        } else {
            modelAndView.addObject("fieldErrors", bindingResult.getFieldErrors());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/users/{userId}/deleteUser", method = RequestMethod.POST)
    public ModelAndView deleteUserPage(ModelAndView modelAndView,
                                       @PathVariable("userId") int userId) {
        userService.delete(userService.findById(userId));
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }

}
