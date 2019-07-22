package com.vironit.mwallet.controllers;

import com.vironit.mwallet.models.dto.RoleDto;
import com.vironit.mwallet.models.dto.UserDto;
import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.services.RoleService;
import com.vironit.mwallet.services.UserService;
import com.vironit.mwallet.services.exception.LoginAlreadyDefinedException;
import com.vironit.mwallet.services.mapper.RoleMapper;
import com.vironit.mwallet.services.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private RoleMapper roleMapper;


    @Qualifier("springValidationService")
    @SuppressWarnings("unused")
    @Autowired
    private Validator validator;

    @GetMapping(value = "/users")
    public ModelAndView allUsersPage(ModelAndView modelAndView) {
        List<UserDto> users = userService.findAll().stream()
                .map(user -> userMapper.toDto(user))
                .collect(Collectors.toList());

        modelAndView.setViewName("userPages/users");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping(value = "/users/{userId}")
    public ModelAndView myUserPage(ModelAndView modelAndView,
                               @PathVariable("userId") int userId) {
        List<UserDto> myUser = new ArrayList<>(); // because in JSP array is expected, to reuse same JSP
        myUser.add(userMapper.toDto(userService.findById(userId)));

        modelAndView.setViewName("userPages/users");
        modelAndView.addObject("users", myUser);
        return modelAndView;
    }

    @GetMapping(value = "/users/addUser")
    public ModelAndView addUserPage(ModelAndView modelAndView) {
        List<RoleDto> roles = roleService.findAll().stream()
                .map(role -> roleMapper.toDto(role))
                .collect(Collectors.toList());

        modelAndView.setViewName("userPages/addUser");
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }

    @PostMapping(value = "/users/addUser")
    public ModelAndView addUser(ModelAndView modelAndView,
                                    @Valid @ModelAttribute("user") UserDto userDto,
                                    BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            try {
                userService.save(userMapper.toEntity(userDto));
                modelAndView.setViewName("main");
                modelAndView.addObject("added", true);
                return modelAndView;
            } catch (LoginAlreadyDefinedException e) {
                log.debug("login already defined. " + userDto);
                bindingResult.addError(new FieldError("user", "login", "login already defined."));
            }
        }
        List<RoleDto> roles = roleService.findAll().stream()
                .map(role -> roleMapper.toDto(role))
                .collect(Collectors.toList());

        modelAndView.setViewName("userPages/addUser");
        modelAndView.addObject("roles", roles);
        modelAndView.addObject("fieldErrors", bindingResult.getFieldErrors());
        return modelAndView;
    }

    @RequestMapping(value = "/users/{userId}/updateUser", method = RequestMethod.GET)
    public ModelAndView updateUserGet(ModelAndView modelAndView,
                                      @PathVariable("userId") int userId) {
        UserDto userDto = userMapper.toDto(userService.findById(userId));
        List<RoleDto> roles = roleService.findAll().stream()
                .map(role -> roleMapper.toDto(role))
                .collect(Collectors.toList());
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
        List<RoleDto> roles = roleService.findAll().stream()
                .map(role -> roleMapper.toDto(role))
                .collect(Collectors.toList());
        roles.remove(userDto.getRole());
        modelAndView.setViewName("userPages/updateUser");
        modelAndView.addObject("roles", roles);
        if (!bindingResult.hasErrors()) {
            userService.update(userMapper.toEntity(userDto));
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
