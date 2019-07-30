package com.vironit.mwallet.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vironit.mwallet.controller.rest.exception.ResourceNotFoundException;
import com.vironit.mwallet.controller.rest.exception.WalletRestControllerException;
import com.vironit.mwallet.controller.rest.exception.WalletValidationErrorException;
import com.vironit.mwallet.model.dto.WalletDto;
import com.vironit.mwallet.model.dto.WalletRestDtoWithUserAndCurrencyId;
import com.vironit.mwallet.model.entity.User;
import com.vironit.mwallet.model.entity.Wallet;
import com.vironit.mwallet.service.UserService;
import com.vironit.mwallet.service.WalletService;
import com.vironit.mwallet.service.mapper.WalletMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "unused"})
@RestController
@Log4j2
@RequestMapping("/api/v1")
class WalletRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletMapper walletMapper;

    @GetMapping(value = "/wallets")
    @PreAuthorize("hasRole('DEFAULT')or hasRole('ADMIN')")
    public ResponseEntity<String> findUserNameByWalletId(
            @RequestParam Map<String, String> allParams) throws JsonProcessingException {
        ObjectMapper Obj = new ObjectMapper();

        if (allParams.containsKey("walletId")) {
            WalletDto walletDto = walletMapper.toDto(walletService
                    .findById(Integer.parseInt(allParams.get("walletId"))));
            if (walletDto != null) {
                String userName = userService
                        .findById(walletDto.getUserId())
                        .getName();
                return new ResponseEntity<>(Obj.writeValueAsString(userName), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/users/{userId}/wallets")
    @PreAuthorize("@securityServiceImpl.checkUserId(authentication,#userId) or hasRole('ADMIN')")
    public ResponseEntity<List<WalletDto>> findAllWalletsOfUser(@PathVariable("userId") int userId)
            throws ResourceNotFoundException {

        User user = userService.findById(userId);
        if (user != null) {
            List<WalletDto> wallets =
                    walletService.findAllByUser(userService.findById(userId)).stream()
                            .map(wallet -> walletMapper.toDto(wallet))
                            .collect(Collectors.toList());
            return new ResponseEntity<>(wallets, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @PostMapping(value = "/users/{userId}/wallets")
    @PreAuthorize("@securityServiceImpl.checkUserId(authentication,#userId) or hasRole('ADMIN')")
    public ResponseEntity<WalletDto> createWallet(@PathVariable("userId") int userId,
                                                  @Valid @RequestBody WalletRestDtoWithUserAndCurrencyId walletRestDtoWithUserAndCurrencyId,
                                                  BindingResult bindingResult)
            throws WalletRestControllerException {

        if (bindingResult.hasErrors()) {
            throw new WalletValidationErrorException(bindingResult.getAllErrors());
        }

        try {
            Wallet wallet = walletMapper.toEntity(walletRestDtoWithUserAndCurrencyId);
            walletService.save(wallet);
            return new ResponseEntity<>(walletMapper.toDto(wallet),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error during wallet saving", e);
            throw new WalletRestControllerException("Error during wallet saving", e);
        }

    }

    @PutMapping(value = "/users/{userId}/wallets")
    public ResponseEntity putWallets(@PathVariable("userId") int userId) {
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping(value = "/users/{userId}/wallets")
    public ResponseEntity deleteWallets(@PathVariable("userId") int userId) {
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

}
