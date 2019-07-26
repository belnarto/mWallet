package com.vironit.mwallet.controller.rest;

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
import com.vironit.mwallet.utils.ErrorTransformator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
@Log4j2
@RequestMapping("/api/v1")
class WalletRestController {

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
        List<String> errors = ErrorTransformator.transformErrors(bindingResult.getAllErrors());
        responseEntity = new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

}
