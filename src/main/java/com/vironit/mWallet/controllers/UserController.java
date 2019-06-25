package com.vironit.mWallet.controllers;

import com.vironit.mWallet.converters.StringToRoleConverter;
import com.vironit.mWallet.models.Role;
import com.vironit.mWallet.models.User;
import com.vironit.mWallet.services.RoleService;
import com.vironit.mWallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
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
    public ModelAndView allUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userPages/users");
        modelAndView.addObject("users", userService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/users/addUser", method = RequestMethod.GET)
    public ModelAndView addUserPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userPages/addUser");
        modelAndView.addObject("roles", roleService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/users/addUser", method = RequestMethod.POST)
    public ModelAndView addUser(@Valid @ModelAttribute("user") User user,
                                BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (!bindingResult.hasErrors()) {
            userService.save(user);
            modelAndView.setViewName("main");
            modelAndView.addObject("added", true);
        } else {
            modelAndView.setViewName("userPages/addUser");
            modelAndView.addObject("roles", roleService.findAll());
            modelAndView.addObject("fieldErrors", bindingResult.getFieldErrors());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/users/editUser", method = RequestMethod.GET)
    public ModelAndView editUserPage(@ModelAttribute("userId") int id) {
        User user = userService.findById(id);
        List<Role> roles = roleService.findAll();
        roles.remove(user.getRole());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userPages/editUser");
        modelAndView.addObject("user", user);
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }

    @RequestMapping(value = "/users/editUser", method = RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute("user") User user,
                                 @ModelAttribute("newRoleName") String newRoleName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userPages/editUser");
        user.setRole(roleService.findByName(newRoleName));
        userService.update(user);
        modelAndView.addObject("updated", true);
        return modelAndView;
    }

    @RequestMapping(value = "/users/deleteUser", method = RequestMethod.GET)
    public ModelAndView deleteUserPage(@ModelAttribute("userId") int id) {
        userService.delete(userService.findById(id));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }

}
