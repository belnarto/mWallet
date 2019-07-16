package com.vironit.mwallet.controllers;

import com.vironit.mwallet.models.Currency;
import com.vironit.mwallet.models.User;
import com.vironit.mwallet.models.Wallet;
import com.vironit.mwallet.models.WalletStatusEnum;
import com.vironit.mwallet.services.CurrencyService;
import com.vironit.mwallet.services.UserService;
import com.vironit.mwallet.services.WalletService;
import com.vironit.mwallet.services.exception.WalletServiceException;
import com.vironit.mwallet.services.exception.WalletStatusException;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class WalletController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private UserService userService;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private WalletService walletService;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private CurrencyService currencyService;

    @RequestMapping(value = "/users/{userId}/wallets", method = RequestMethod.GET)
    public ModelAndView userWalletsPage(ModelAndView modelAndView,
                                        @PathVariable("userId") int userId) {
        modelAndView.setViewName("walletPages/wallets");
        modelAndView.addObject("userId", userId);
        List<Wallet> wallets = walletService.findAllByUser(userService.findById(userId));
        modelAndView.addObject("wallets", wallets);
        return modelAndView;
    }

    @RequestMapping(value = "/users/{userId}/wallets/addWallet", method = RequestMethod.GET)
    public ModelAndView addWalletPage(ModelAndView modelAndView,
                                      @PathVariable("userId") int userId) {
        modelAndView.setViewName("walletPages/addWallet");
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("currencies", currencyService.findAll());
        return modelAndView;
    }

    // TODO add business logic validation
    @RequestMapping(value = "/users/{userId}/wallets/addWallet", method = RequestMethod.POST)
    public ModelAndView addWallet(ModelAndView modelAndView,
                                  @PathVariable("userId") int userId,
                                  @Valid @ModelAttribute("wallet") Wallet wallet,
                                  BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            walletService.save(wallet);
        }
        modelAndView = userWalletsPage(modelAndView, userId);
        modelAndView.addObject("fieldErrors", bindingResult.getFieldErrors());
        return modelAndView;
    }

    @RequestMapping(value = "/users/{userId}/wallets/{walletId}/editWallet", method = RequestMethod.GET)
    public ModelAndView editWalletPage(ModelAndView modelAndView,
                                       @PathVariable("userId") int userId,
                                       @PathVariable("walletId") int walletId) {
        modelAndView.setViewName("walletPages/editWallet");
        Wallet wallet = walletService.findById(walletId);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("wallet", wallet);
        modelAndView.addObject("currencies", currencyService.findAll()
                .stream()
                .filter(c -> !c.equals(wallet.getCurrency()))
                .collect(Collectors.toList()));
        modelAndView.addObject("statuses", Arrays.stream(WalletStatusEnum.values())
                .filter(s -> !s.equals(wallet.getWalletStatus()))
                .collect(Collectors.toList()));
        return modelAndView;
    }

    @RequestMapping(value = "/users/{userId}/wallets/{walletId}/editWallet", method = RequestMethod.POST)
    public ModelAndView editWallet(ModelAndView modelAndView,
                                   @PathVariable("userId") int userId,
                                   @PathVariable("walletId") int walletId,
                                   @Valid @ModelAttribute("wallet") Wallet wallet,
                                   BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            walletService.update(wallet);
        }
        modelAndView = userWalletsPage(modelAndView, userId);
        modelAndView.addObject("fieldErrors", bindingResult.getFieldErrors());
        return modelAndView;
    }

    @RequestMapping(value = "/users/{userId}/wallets/{walletId}/deleteWallet", method = RequestMethod.POST)
    public ModelAndView deleteWallet(ModelAndView modelAndView,
                                     @PathVariable("userId") int userId,
                                     @PathVariable("walletId") int walletId) {
        walletService.delete(walletService.findById(walletId));
        modelAndView.setViewName("redirect:/users/{userId}/wallets");
        return modelAndView;
    }

    @RequestMapping(value = "/users/{userId}/wallets/{walletId}/addBalance", method = RequestMethod.GET)
    public ModelAndView addBalancePage(ModelAndView modelAndView,
                                       @PathVariable("userId") int userId,
                                       @PathVariable("walletId") int walletId) {
        modelAndView.setViewName("walletPages/addBalance");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @RequestMapping(value = "/users/{userId}/wallets/{walletId}/addBalance", method = RequestMethod.POST)
    public ModelAndView addBalance(ModelAndView modelAndView,
                                   @PathVariable("userId") int userId,
                                   @PathVariable("walletId") int walletId,
                                   @ModelAttribute("amountToAdd") double amountToAdd,
                                   BindingResult bindingResult) {
        Wallet wallet = walletService.findById(walletId);
        try {
            walletService.addBalance(wallet, amountToAdd);
        } catch (WalletServiceException e) {
            log.debug("can't add balance to wallet " + wallet
            + " because " + e);
            bindingResult.addError(new FieldError("wallet", "status", e.getMessage()));
        }
        modelAndView = userWalletsPage(modelAndView, userId);
        modelAndView.addObject("fieldErrors", bindingResult.getFieldErrors());
        return modelAndView;
    }

    @RequestMapping(value = "/users/{userId}/wallets/{walletId}/reduceBalance", method = RequestMethod.GET)
    public ModelAndView reduceBalancePage(ModelAndView modelAndView,
                                          @PathVariable("userId") int userId,
                                          @PathVariable("walletId") int walletId) {
        modelAndView.setViewName("walletPages/reduceBalance");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @RequestMapping(value = "/users/{userId}/wallets/{walletId}/reduceBalance", method = RequestMethod.POST)
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

    @RequestMapping(value = "/users/{userId}/wallets/{walletId}/transferMoney", method = RequestMethod.GET)
    public ModelAndView transferMoneyPage(ModelAndView modelAndView,
                                          @PathVariable("userId") int userId,
                                          @PathVariable("walletId") int walletId) {
        modelAndView.setViewName("walletPages/transferMoney");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @RequestMapping(value = "/users/{userId}/wallets/{walletId}/transferMoney", method = RequestMethod.POST)
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

    @RequestMapping(value = "/myWallets", method = RequestMethod.GET)
    public ModelAndView walletsPage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("walletPages/wallets");
        String username;
        username = authentication.getName();
        modelAndView.addObject("currURL", "myWallets");
        modelAndView.addObject("id", userService.findByLogin(username).getId());
        modelAndView.addObject("wallets", walletService.
                findAllByUser(userService.findByLogin(username)));
        return modelAndView;
    }

}
