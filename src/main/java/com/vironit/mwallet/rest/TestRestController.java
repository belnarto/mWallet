package com.vironit.mwallet.rest;

import com.vironit.mwallet.models.dto.CurrencyDto;
import com.vironit.mwallet.models.dto.RoleDto;
import com.vironit.mwallet.models.dto.UserDto;
import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.models.entity.Wallet;
import com.vironit.mwallet.services.UserService;
import com.vironit.mwallet.services.WalletService;
import com.vironit.mwallet.services.exception.LoginAlreadyDefinedException;
import com.vironit.mwallet.services.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
@Log4j2
@RequestMapping("/api/v1")
class TestRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "/users")
    public List<UserDto> findAllUsers() {
        return userService.findAll().stream()
                .map(user -> userMapper.toDto(user))
                .collect(Collectors.toList());
    }

    @SuppressWarnings({"StringBufferReplaceableByString", "unchecked"})
    @PostMapping(value = "/users")
    public ResponseEntity createUser(@Valid @RequestBody UserDto userDto,
                                     BindingResult bindingResult) {
        ResponseEntity responseEntity;
        if (!bindingResult.hasErrors()) {
            try {
                User user = userMapper.toEntity(userDto);
                userService.save(user);
                responseEntity = new ResponseEntity(userMapper.toDto(user), HttpStatus.CREATED);
                return responseEntity;
            } catch (LoginAlreadyDefinedException e) {
                bindingResult.addError(new FieldError("user", "login", e.getMessage()));
            } catch (Exception e) {
                log.debug("Error during user saving" + e.getMessage());
                responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                return responseEntity;
            }
        }
        List<String> errors = bindingResult.getFieldErrors().stream()
                .map(fieldError -> new StringBuilder()
                        .append("field: ")
                        .append(fieldError.getField())
                        .append(", rejected value: ")
                        .append(fieldError.getRejectedValue())
                        .append(", message: ")
                        .append(fieldError.getDefaultMessage())
                        .toString())
                .collect(Collectors.toList());
        responseEntity = new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @GetMapping(value = "/users/{userId}")
    public UserDto findUserById(@PathVariable("userId") int userId) {
        return userMapper.toDto(userService.findById(userId));
    }

    @PutMapping(value = "/users/{userId}/updateUser")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable("userId") int userId,
                              @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        Set<Wallet> wallets = walletService.findAllByUser(user);
        user.setWallets(wallets);
        try {
            userService.update(user);
        } catch (LoginAlreadyDefinedException e) {
            log.debug("Error during user saving", e);
        }
        return userMapper.toDto(user);
    }

    @DeleteMapping(value = "/users/{userId}/deleteUser")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("userId") int userId) {
        userService.delete(userService.findById(userId));
    }
}
