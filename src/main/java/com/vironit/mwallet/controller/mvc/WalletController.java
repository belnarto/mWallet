package com.vironit.mwallet.controller.mvc;

import com.vironit.mwallet.controller.mvc.exception.WalletControllerException;
import com.vironit.mwallet.models.dto.CurrencyDto;
import com.vironit.mwallet.models.dto.WalletDto;
import com.vironit.mwallet.models.attributes.WalletStatusEnum;
import com.vironit.mwallet.models.entity.Wallet;
import com.vironit.mwallet.services.CurrencyService;
import com.vironit.mwallet.services.UserService;
import com.vironit.mwallet.services.WalletService;
import com.vironit.mwallet.services.exception.WalletServiceException;
import com.vironit.mwallet.services.mapper.CurrencyMapper;
import com.vironit.mwallet.services.mapper.WalletMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@Log4j2
public class WalletController {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private CurrencyMapper currencyMapper;

    @GetMapping(value = "/users/{userId}/wallets")
    @PreAuthorize("@securityService.checkUserId(authentication,#userId) or hasRole('ADMIN')")
    public ModelAndView userWalletsPage(ModelAndView modelAndView,
                                        @PathVariable("userId") int userId) {
        List<WalletDto> wallets = walletService.findAllByUser(userService.findById(userId)).stream()
                .map(wallet -> walletMapper.toDto(wallet))
                .collect(Collectors.toList());

        modelAndView.setViewName("walletPages/wallets");
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("wallets", wallets);
        return modelAndView;
    }

    @GetMapping(value = "/users/{userId}/wallets/addWallet")
    @PreAuthorize("@securityService.checkUserId(authentication,#userId) or hasRole('ADMIN')")
    public ModelAndView addWalletPage(ModelAndView modelAndView,
                                      @PathVariable("userId") int userId) {
        List<CurrencyDto> currencies = currencyService.findAll().stream()
                .map(currency -> currencyMapper.toDto(currency))
                .collect(Collectors.toList());

        modelAndView.setViewName("walletPages/addWallet");
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("currencies", currencies);
        return modelAndView;
    }

    @PostMapping(value = "/users/{userId}/wallets/addWallet")
    @PreAuthorize("@securityService.checkUserId(authentication,#userId) or hasRole('ADMIN')")
    public ModelAndView addWallet(ModelAndView modelAndView,
                                  @PathVariable("userId") int userId,
                                  @Valid @ModelAttribute("wallet") WalletDto walletDto,
                                  BindingResult bindingResult) throws WalletControllerException {
        if (walletDto.getUserId() != userId) {
            log.debug("user id not matches");
            throw new WalletControllerException("user id not matches");
        }

        if (!bindingResult.hasErrors()) {
            try {
                walletService.save(walletMapper.toEntity(walletDto));
            } catch (WalletServiceException e) {
                log.error("wallet service exception", e);
                throw new WalletControllerException("error occurred during addition new wallet", e);
            }
        }

        modelAndView = userWalletsPage(modelAndView, userId);
        modelAndView.addObject("fieldErrors", bindingResult.getFieldErrors());
        return modelAndView;
    }

    @GetMapping(value = "/users/{userId}/wallets/{walletId}/editWallet")
    @PreAuthorize("(@securityService.checkUserId(authentication,#userId)" +
            " and @securityService.checkWalletId(authentication,#walletId))" +
            " or hasRole('ADMIN')")
    public ModelAndView editWalletPage(ModelAndView modelAndView,
                                       @PathVariable("userId") int userId,
                                       @PathVariable("walletId") int walletId) {
        WalletDto walletDto = walletMapper.toDto(walletService.findById(walletId));
        List<CurrencyDto> currencies = currencyService.findAll().stream()
                .map(currency -> currencyMapper.toDto(currency))
                .filter(c -> !c.equals(walletDto.getCurrency()))
                .collect(Collectors.toList());
        List<WalletStatusEnum> statuses = Arrays.stream(WalletStatusEnum.values())
                .filter(s -> !s.equals(walletDto.getWalletStatus()))
                .collect(Collectors.toList());

        modelAndView.setViewName("walletPages/editWallet");
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("wallet", walletDto);
        modelAndView.addObject("currencies", currencies);
        modelAndView.addObject("statuses", statuses);
        return modelAndView;
    }

    @PostMapping(value = "/users/{userId}/wallets/{walletId}/editWallet")
    @PreAuthorize("(@securityService.checkUserId(authentication,#userId)" +
            " and @securityService.checkWalletId(authentication,#walletId))" +
            " or hasRole('ADMIN')")
    public ModelAndView editWallet(ModelAndView modelAndView,
                                   @PathVariable("userId") int userId,
                                   @PathVariable("walletId") int walletId,
                                   @Valid @ModelAttribute("wallet") WalletDto walletDto,
                                   BindingResult bindingResult) throws WalletControllerException {
        if (!bindingResult.hasErrors()) {
            try {
                walletService.update(walletMapper.toEntity(walletDto));
            } catch (Exception e) {
                log.error("error occurred during updating wallet", e);
                throw new WalletControllerException("error occurred during updating wallet", e);
            }
        }
        modelAndView = userWalletsPage(modelAndView, userId);
        modelAndView.addObject("fieldErrors", bindingResult.getFieldErrors());
        return modelAndView;
    }

    @PostMapping(value = "/users/{userId}/wallets/{walletId}/deleteWallet")
    @PreAuthorize("(@securityService.checkUserId(authentication,#userId)" +
            " and @securityService.checkWalletId(authentication,#walletId))" +
            " or hasRole('ADMIN')")
    public ModelAndView deleteWallet(ModelAndView modelAndView,
                                     @PathVariable("userId") int userId,
                                     @PathVariable("walletId") int walletId) throws WalletControllerException {
        try {
            walletService.delete(walletService.findById(walletId));
        } catch (Exception e) {
            log.error("error occurred during deletion wallet", e);
            throw new WalletControllerException("error occurred during deletion wallet", e);
        }
        modelAndView.setViewName("redirect:/users/{userId}/wallets");
        return modelAndView;
    }

    @GetMapping(value = "/users/{userId}/wallets/{walletId}/addBalance")
    @PreAuthorize("(@securityService.checkUserId(authentication,#userId)" +
            " and @securityService.checkWalletId(authentication,#walletId))" +
            " or hasRole('ADMIN')")
    public ModelAndView addBalancePage(ModelAndView modelAndView,
                                       @PathVariable("userId") int userId,
                                       @PathVariable("walletId") int walletId) {
        modelAndView.setViewName("walletPages/addBalance");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping(value = "/users/{userId}/wallets/{walletId}/addBalance")
    @PreAuthorize("(@securityService.checkUserId(authentication,#userId)" +
            " and @securityService.checkWalletId(authentication,#walletId))" +
            " or hasRole('ADMIN')")
    public ModelAndView addBalance(ModelAndView modelAndView,
                                   @PathVariable("userId") int userId,
                                   @PathVariable("walletId") int walletId,
                                   @ModelAttribute("amountToAdd") double amountToAdd,
                                   BindingResult bindingResult) {
        Wallet wallet = walletService.findById(walletId);
        try {
            walletService.addBalance(wallet, amountToAdd);
        } catch (WalletServiceException e) {
            log.error("can't add balance to wallet " + wallet
                    + " because " + e);
            bindingResult.addError(new FieldError("wallet", "status", e.getMessage()));
        }
        modelAndView = userWalletsPage(modelAndView, userId);
        modelAndView.addObject("fieldErrors", bindingResult.getFieldErrors());
        return modelAndView;
    }

    @GetMapping(value = "/users/{userId}/wallets/{walletId}/reduceBalance")
    @PreAuthorize("(@securityService.checkUserId(authentication,#userId)" +
            " and @securityService.checkWalletId(authentication,#walletId))" +
            " or hasRole('ADMIN')")
    public ModelAndView reduceBalancePage(ModelAndView modelAndView,
                                          @PathVariable("userId") int userId,
                                          @PathVariable("walletId") int walletId) {
        modelAndView.setViewName("walletPages/reduceBalance");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping(value = "/users/{userId}/wallets/{walletId}/reduceBalance")
    @PreAuthorize("(@securityService.checkUserId(authentication,#userId)" +
            " and @securityService.checkWalletId(authentication,#walletId))" +
            " or hasRole('ADMIN')")
    public ModelAndView reduceBalance(ModelAndView modelAndView,
                                      @PathVariable("userId") int userId,
                                      @PathVariable("walletId") int walletId,
                                      @ModelAttribute("amountToReduce") double amountToReduce,
                                      BindingResult bindingResult) {
        Wallet wallet = walletService.findById(walletId);
        try {
            walletService.reduceBalance(wallet, amountToReduce);
        } catch (WalletServiceException e) {
            log.debug("can't reduce balance of wallet " + wallet
                    + " because " + e);
            bindingResult.addError(new FieldError("wallet", "status", e.getMessage()));
        }
        modelAndView = userWalletsPage(modelAndView, userId);
        modelAndView.addObject("fieldErrors", bindingResult.getFieldErrors());
        return modelAndView;
    }

    @GetMapping(value = "/users/{userId}/wallets/{walletId}/transferMoney")
    @PreAuthorize("(@securityService.checkUserId(authentication,#userId)" +
            " and @securityService.checkWalletId(authentication,#walletId))" +
            " or hasRole('ADMIN')")
    public ModelAndView transferMoneyPage(ModelAndView modelAndView,
                                          @PathVariable("userId") int userId,
                                          @PathVariable("walletId") int walletId) {
        modelAndView.setViewName("walletPages/transferMoney");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping(value = "/users/{userId}/wallets/{walletId}/transferMoney")
    @PreAuthorize("(@securityService.checkUserId(authentication,#userId)" +
            " and @securityService.checkWalletId(authentication,#walletId))" +
            " or hasRole('ADMIN')")
    public ModelAndView transferMoney(ModelAndView modelAndView,
                                      @PathVariable("userId") int userId,
                                      @PathVariable("walletId") int walletId,
                                      @ModelAttribute("amountToTransfer") double amountToTransfer,
                                      @ModelAttribute("targetWalletId") int targetWalletId,
                                      BindingResult bindingResult) {
        try {
            Wallet wallet = walletService.findById(walletId);
            Wallet targetWallet = walletService.findById(targetWalletId);
            walletService.transferMoney(wallet, targetWallet, amountToTransfer);
        } catch (WalletServiceException e) {
            log.debug("can't transfer money from wallet: " + walletId
                    + " to wallet: " + targetWalletId
                    + " because " + e);
            bindingResult.addError(new FieldError("wallet", "status", e.getMessage()));
        }
        modelAndView = userWalletsPage(modelAndView, userId);
        modelAndView.addObject("fieldErrors", bindingResult.getFieldErrors());
        return modelAndView;
    }

}
