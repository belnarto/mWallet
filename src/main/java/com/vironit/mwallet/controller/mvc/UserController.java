package com.vironit.mwallet.controller.mvc;

import com.vironit.mwallet.controller.mvc.exception.UserControllerException;
import com.vironit.mwallet.model.dto.RoleDto;
import com.vironit.mwallet.model.dto.UserDto;
import com.vironit.mwallet.model.entity.User;
import com.vironit.mwallet.model.entity.Wallet;
import com.vironit.mwallet.service.RoleService;
import com.vironit.mwallet.service.UserService;
import com.vironit.mwallet.service.WalletService;
import com.vironit.mwallet.service.exception.LoginAlreadyDefinedException;
import com.vironit.mwallet.service.mapper.RoleMapper;
import com.vironit.mwallet.service.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    private WalletService walletService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Qualifier("springValidationService")
    @SuppressWarnings("unused")
    @Autowired
    private Validator validator;

    @GetMapping(value = "/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView allUsersPage(ModelAndView modelAndView) {
        List<UserDto> users = userService.findAll().stream()
                .map(user -> userMapper.toDto(user))
                .collect(Collectors.toList());

        modelAndView.setViewName("userPages/users");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping(value = "/users/{userId}")
    @PreAuthorize("@securityServiceImpl.checkUserId(authentication,#userId) or hasRole('ADMIN')")
    public ModelAndView myUserPage(ModelAndView modelAndView,
                                   @PathVariable("userId") int userId) {
        List<UserDto> user = new ArrayList<>(); // because in JSP array is expected, to reuse same JSP
        user.add(userMapper.toDto(userService.findById(userId)));

        modelAndView.setViewName("userPages/users");
        modelAndView.addObject("users", user);
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
                                BindingResult bindingResult) throws UserControllerException {
        if (!bindingResult.hasErrors()) {
            try {
                userService.save(userMapper.toEntity(userDto));
                modelAndView.setViewName("main");
                modelAndView.addObject("added", true);
                return modelAndView;
            } catch (LoginAlreadyDefinedException e) {
                log.debug("login already defined. " + userDto);
                bindingResult.addError(
                        new FieldError("user", "login", "login already defined."));
            } catch (Exception e) {
                log.error("error occurred during addition new user ", e);
                throw new UserControllerException("error occurred during addition new user", e);
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

    @GetMapping(value = "/users/{userId}/updateUser")
    @PreAuthorize("@securityServiceImpl.checkUserId(authentication,#userId) or hasRole('ADMIN')")
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

    @SuppressWarnings("unused")
    @PostMapping(value = "/users/{userId}/updateUser")
    @PreAuthorize("@securityServiceImpl.checkUserId(authentication,#userId) or hasRole('ADMIN')")
    public ModelAndView updateUserPost(ModelAndView modelAndView,
                                       @PathVariable("userId") int userId,
                                       @Valid @ModelAttribute("user") UserDto userDto,
                                       BindingResult bindingResult) throws UserControllerException {
        List<RoleDto> roles = roleService.findAll().stream()
                .map(role -> roleMapper.toDto(role))
                .collect(Collectors.toList());
        roles.remove(userDto.getRole());

        if (!bindingResult.hasErrors()) {
            User user = userMapper.toEntity(userDto);
            Set<Wallet> wallets = walletService.findAllByUser(user);
            user.setWallets(wallets);
            try {
                userService.update(user);
                modelAndView.addObject("updated", true);
            } catch (LoginAlreadyDefinedException e) {
                log.debug("login already defined. " + user);
                bindingResult.addError(
                        new FieldError("user", "login", "login already defined."));
            } catch (Exception e) {
                log.error("error occurred during updating user ", e);
                throw new UserControllerException("error occurred during updating user", e);
            }
        }

        modelAndView.setViewName("userPages/updateUser");
        modelAndView.addObject("roles", roles);
        modelAndView.addObject("fieldErrors", bindingResult.getFieldErrors());
        return modelAndView;
    }

    @PostMapping(value = "/users/{userId}/deleteUser")
    @PreAuthorize("@securityServiceImpl.checkUserId(authentication,#userId) or hasRole('ADMIN')")
    public ModelAndView deleteUserPage(ModelAndView modelAndView,
                                       @PathVariable("userId") int userId) throws UserControllerException {
        try {
            userService.delete(userService.findById(userId));
        } catch (Exception e) {
            log.error("error occurred during deletion user ", e);
            throw new UserControllerException("error occurred during deletion user", e);
        }
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }

}
