package com.vironit.mWallet.controllers;

import com.vironit.mWallet.models.Role;
import com.vironit.mWallet.models.User;
import com.vironit.mWallet.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.vironit.mWallet.services.UserService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView allUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userPages/users");
        modelAndView.addObject("users", userService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/users/addUser", method = RequestMethod.GET)
    public ModelAndView addUserPage() {
        List<Role> roles = roleService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userPages/addUser");
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }

    @RequestMapping(value = "/users/addUser", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("user") User user,
                                @ModelAttribute("newRoleName") String newRoleName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userPages/addUser");
        user.setRole(roleService.findByName(newRoleName));
        userService.save(user);
        modelAndView.addObject("updated", true);
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
