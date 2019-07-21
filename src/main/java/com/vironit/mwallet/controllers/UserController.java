package com.vironit.mwallet.controllers;

import com.vironit.mwallet.models.dto.RoleDto;
import com.vironit.mwallet.models.dto.UserDto;
import com.vironit.mwallet.services.RoleService;
import com.vironit.mwallet.services.UserService;
import com.vironit.mwallet.services.exception.LoginAlreadyDefinedException;
import com.vironit.mwallet.services.mapper.UserMapper;
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

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@Log4j2
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserMapper userMapper;


    @Qualifier("springValidationService")
    @SuppressWarnings("unused")
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
        List<UserDto> myUser = new ArrayList<>(); // because in JSP array is expected
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
                                    @ModelAttribute("user") UserDto userDto,
                                    BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            try {
                userService.save(userDto);
                modelAndView.setViewName("main");
                modelAndView.addObject("added", true);
                return modelAndView;
            } catch (LoginAlreadyDefinedException e) {
                log.debug("login already defined. " + userDto);
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
        UserDto userDto = userService.findById(userId);
        List<RoleDto> roles = roleService.findAll();
        roles.remove(userDto.getRole());
        modelAndView.setViewName("userPages/updateUser");
        modelAndView.addObject("user", userDto);
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }


    @SuppressWarnings("MVCPathVariableInspection")
    @RequestMapping(value = "/users/{userId}/updateUser", method = RequestMethod.POST)
    public ModelAndView updateUserPost(ModelAndView modelAndView,
                                       @ModelAttribute("user") UserDto userDto,
                                       BindingResult bindingResult) {
        List<RoleDto> roles = roleService.findAll();
        roles.remove(userDto.getRole());
        modelAndView.setViewName("userPages/updateUser");
        modelAndView.addObject("roles", roles);
        if (!bindingResult.hasErrors()) {
            userService.update(userDto);
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
