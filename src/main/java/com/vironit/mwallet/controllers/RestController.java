package com.vironit.mwallet.controllers;

import com.vironit.mwallet.models.dto.CurrencyDto;
import com.vironit.mwallet.models.dto.UserRestDto;
import com.vironit.mwallet.models.dto.UserRestDtoWithoutPassword;
import com.vironit.mwallet.models.dto.WalletDto;
import com.vironit.mwallet.models.dto.WalletRestDtoWithUserAndCurrencyId;
import com.vironit.mwallet.models.entity.Currency;
import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.models.entity.Wallet;
import com.vironit.mwallet.services.CurrencyService;
import com.vironit.mwallet.services.UserService;
import com.vironit.mwallet.services.WalletService;
import com.vironit.mwallet.services.mapper.CurrencyMapper;
import com.vironit.mwallet.services.mapper.UserMapper;
import com.vironit.mwallet.services.mapper.WalletMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@org.springframework.web.bind.annotation.RestController
@Log4j2
@RequestMapping("/api/v1")
class RestController {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private CurrencyMapper currencyMapper;

    @GetMapping(value = "/currencies")
    public List<CurrencyDto> findAllCurrencies() {
        return currencyService.findAll().stream()
                .map(currency -> currencyMapper.toDto(currency))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/currencies")
    public ResponseEntity createCurrency(@Valid @RequestBody CurrencyDto currencyDto,
                                     BindingResult bindingResult) {
        ResponseEntity responseEntity;

        if (!bindingResult.hasErrors()) {
            try {
                Currency currency = currencyMapper.toEntity(currencyDto);
                currencyService.save(currency);
                responseEntity = new ResponseEntity(currencyMapper.toDto(currency),
                        HttpStatus.CREATED);
                return responseEntity;
            } catch (Exception e) {
                log.debug("Error during currency saving", e);
                responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                return responseEntity;
            }
        }
        List<String> errors = transformErrors(bindingResult.getAllErrors());
        responseEntity = new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    //@PreAuthorize("hasRole('ADMIN')")

    @GetMapping(value = "/users")
    public List<UserRestDtoWithoutPassword> findAllUsers() {
        return userService.findAll().stream()
                .map(user -> userMapper.toRestDtoWithoutPassword(user))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/users")
    public ResponseEntity createUser(@Valid @RequestBody UserRestDto userRestDto,
                                     BindingResult bindingResult) {
        ResponseEntity responseEntity;

        if (!bindingResult.hasErrors()) {
            try {
                User user = userMapper.toEntity(userRestDto);
                userService.save(user);
                responseEntity = new ResponseEntity(userMapper.toRestDtoWithoutPassword(user),
                        HttpStatus.CREATED);
                return responseEntity;
            } catch (Exception e) {
                log.debug("Error during user saving", e);
                responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                return responseEntity;
            }
        }
        List<String> errors = transformErrors(bindingResult.getAllErrors());
        responseEntity = new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @SuppressWarnings("unchecked")
    @PreAuthorize("@securityService.checkUserId(authentication,#userId)")
    @GetMapping(value = "/users/{userId}")
    public ResponseEntity findUserById(@PathVariable("userId") int userId) {
        ResponseEntity responseEntity;
        User user = userService.findById(userId);

        if (user != null) {
            responseEntity = new ResponseEntity(userMapper.toRestDtoWithoutPassword(user),
                    HttpStatus.OK);
            return responseEntity;
        } else {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
            return responseEntity;
        }
    }

    @SuppressWarnings("unused")
    @PostMapping(value = "/users/{userId}")
    public ResponseEntity postUserById(@PathVariable("userId") int userId) {
        ResponseEntity responseEntity;
        responseEntity = new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
        return responseEntity;
    }

    @SuppressWarnings({"unchecked", "unused"})
    @PutMapping(value = "/users/{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") int userId,
                                     @Valid @RequestBody UserRestDto userRestDto,
                                     BindingResult bindingResult) {
        ResponseEntity responseEntity;

        if (!bindingResult.hasErrors()) {
            try {
                User user = userMapper.toEntity(userRestDto);
                userService.update(user);
                responseEntity = new ResponseEntity(userMapper.toRestDtoWithoutPassword(user),
                        HttpStatus.OK);
                return responseEntity;
            } catch (Exception e) {
                log.debug("Error during user updating", e);
                responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                return responseEntity;
            }
        }
        List<String> errors = transformErrors(bindingResult.getAllErrors());
        responseEntity = new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @DeleteMapping(value = "/users/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") int userId) {
        ResponseEntity responseEntity;
        User user = userService.findById(userId);

        if (user != null) {
            userService.delete(user);
            responseEntity = new ResponseEntity(HttpStatus.OK);
            return responseEntity;
        } else {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
            return responseEntity;
        }
    }

    @SuppressWarnings("unchecked")
    @GetMapping(value = "/users/{userId}/wallets")
    public ResponseEntity findAllWalletsOfUser(@PathVariable("userId") int userId) {
        ResponseEntity responseEntity;
        User user = userService.findById(userId);

        if (user != null) {
            List<WalletDto> wallets = walletService.findAllByUser(userService.findById(userId)).stream()
                    .map(wallet -> walletMapper.toDto(wallet))
                    .collect(Collectors.toList());
            responseEntity = new ResponseEntity(wallets, HttpStatus.OK);
            return responseEntity;
        } else {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
            return responseEntity;
        }
    }

    @SuppressWarnings("unchecked")
    @PostMapping(value = "/users/{userId}/wallets")
    public ResponseEntity createWallet(@Valid @RequestBody WalletRestDtoWithUserAndCurrencyId walletRestDtoWithUserAndCurrencyId,
                                       BindingResult bindingResult,
                                       @PathVariable("userId") int userId) {
        ResponseEntity responseEntity;

        if (!bindingResult.hasErrors()) {
            try {
                Wallet wallet = walletMapper.toEntity(walletRestDtoWithUserAndCurrencyId);
                walletService.save(wallet);
                responseEntity = new ResponseEntity(walletMapper.toDto(wallet),
                        HttpStatus.CREATED);
                return responseEntity;
            } catch (Exception e) {
                log.debug("Error during wallet saving", e);
                responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                return responseEntity;
            }
        }
        List<String> errors = transformErrors(bindingResult.getAllErrors());
        responseEntity = new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private List<String> transformErrors(List<ObjectError> errors) {
        List<String> result = new LinkedList<>();

        result.addAll(errors.stream()
                .filter(objectError -> objectError instanceof FieldError)
                .map(objectError -> (FieldError) objectError)
                .map(fieldError -> new StringBuilder()
                        .append("field: ")
                        .append(fieldError.getField())
                        .append(", rejected value: ")
                        .append(fieldError.getRejectedValue())
                        .append(", message: ")
                        .append(fieldError.getDefaultMessage())
                        .toString())
                .collect(Collectors.toList()));

        result.addAll(errors.stream()
                .filter(objectError -> objectError.getClass().equals(ObjectError.class))
                .map(objectError -> new StringBuilder()
                        .append("error: ")
                        .append(objectError.getDefaultMessage())
                        .toString())
                .collect(Collectors.toList()));

        return result;
    }
}
