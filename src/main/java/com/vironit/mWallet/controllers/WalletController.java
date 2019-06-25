package com.vironit.mWallet.controllers;

import com.vironit.mWallet.models.Currency;
import com.vironit.mWallet.models.User;
import com.vironit.mWallet.models.Wallet;
import com.vironit.mWallet.models.WalletStatusEnum;
import com.vironit.mWallet.services.CurrencyService;
import com.vironit.mWallet.services.UserService;
import com.vironit.mWallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
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

    @RequestMapping(value = "/users/{id}/wallets", method = RequestMethod.GET)
    public ModelAndView userWalletsPage(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("walletPages/wallets");
        modelAndView.addObject("id", id);
        List<Wallet> wallets = walletService.findAllByUser(userService.findById(id));
        modelAndView.addObject("wallets", wallets);
        modelAndView.addObject("currURL", "/users/{id}/wallets" );
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/wallets/{id2}/addBalance", method = RequestMethod.GET)
    public ModelAndView addBalancePage(@PathVariable("id") int id,
                                       @PathVariable("id2") int id2) {
        Wallet wallet = walletService.findById(id2);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("walletPages/addBalance");
        modelAndView.addObject("id", id);
        modelAndView.addObject("wallet", wallet);
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/wallets/{id2}/addBalance", method = RequestMethod.POST)
    public ModelAndView addBalance(@SuppressWarnings("unused") @PathVariable("id") int id,
                                   @PathVariable("id2") int id2,
                                   @ModelAttribute("amountToAdd") double amountToAdd) {
        ModelAndView modelAndView = new ModelAndView();
        Wallet wallet = walletService.findById(id2);
        walletService.addBalance(wallet, amountToAdd);
        modelAndView.setViewName("redirect:/users/{id}/wallets");
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/wallets/{id2}/reduceBalance", method = RequestMethod.GET)
    public ModelAndView reduceBalancePage(@PathVariable("id") int id,
                                          @PathVariable("id2") int id2) {
        Wallet wallet = walletService.findById(id2);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("walletPages/reduceBalance");
        modelAndView.addObject("id", id);
        modelAndView.addObject("wallet", wallet);
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/wallets/{id2}/reduceBalance", method = RequestMethod.POST)
    public ModelAndView reduceBalance(@SuppressWarnings("unused") @PathVariable("id") int id,
                                      @PathVariable("id2") int id2,
                                      @ModelAttribute("amountToReduce") double amountToReduce) {
        ModelAndView modelAndView = new ModelAndView();
        Wallet wallet = walletService.findById(id2);
        walletService.reduceBalance(wallet, amountToReduce);
        modelAndView.setViewName("redirect:/users/{id}/wallets");
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/wallets/{id2}/transferMoney", method = RequestMethod.GET)
    public ModelAndView transferMoneyPage(@PathVariable("id") int id,
                                          @PathVariable("id2") int id2) {
        Wallet wallet = walletService.findById(id2);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("walletPages/transferMoney");
        modelAndView.addObject("id", id);
        modelAndView.addObject("wallet", wallet);
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/wallets/{id2}/transferMoney", method = RequestMethod.POST)
    public ModelAndView transferMoney(@SuppressWarnings("unused") @PathVariable("id") int id,
                                      @PathVariable("id2") int id2,
                                      @ModelAttribute("amountToTransfer") double amountToTransfer,
                                      @ModelAttribute("targetWallet") int targetWallet) {
        ModelAndView modelAndView = new ModelAndView();
        Wallet wallet = walletService.findById(id2);
        Wallet target_Wallet = walletService.findById(targetWallet);
        walletService.transferMoney(wallet, target_Wallet, amountToTransfer);
        modelAndView.setViewName("redirect:/users/{id}/wallets");
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/wallets/{id2}/editWallet", method = RequestMethod.GET)
    public ModelAndView editWalletPage(@PathVariable("id") int id,
                                       @PathVariable("id2") int id2) {
        Wallet wallet = walletService.findById(id2);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("walletPages/editWallet");
        modelAndView.addObject("id", id);
        modelAndView.addObject("wallet", wallet);
        modelAndView.addObject("currencies", currencyService.findAll()
                .stream()
                .filter(c -> !c.equals(wallet.getCurrency()))
                .collect(Collectors.toList()));
        modelAndView.addObject("statuses", Arrays.stream(WalletStatusEnum.values())
                .filter(s -> !s.equals(wallet.getStatus()))
                .collect(Collectors.toList()));
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/wallets/{id2}/editWallet", method = RequestMethod.POST)
    public ModelAndView editWallet(@SuppressWarnings("unused") @PathVariable("id") int id,
                                   @PathVariable("id2") int id2,
                                   @ModelAttribute("wallet") Currency currencyNew,
                                   @ModelAttribute("status") WalletStatusEnum walletStatusEnumNew) {
        ModelAndView modelAndView = new ModelAndView();
        currencyNew = currencyService.findByName(currencyNew.getName());
        Wallet wallet = walletService.findById(id2);
        double balance = wallet.getBalance();
        Currency currencyOld = wallet.getCurrency();
        double targetValue = currencyOld.getRate() * balance; // to BYN
        targetValue = targetValue / currencyNew.getRate(); // to target Currency
        targetValue = new BigDecimal(targetValue)
                .setScale(3, RoundingMode.DOWN)
                .doubleValue();
        wallet.setBalance(targetValue);
        wallet.setCurrency(currencyNew);
        wallet.setStatus(walletStatusEnumNew);
        walletService.update(wallet);
        modelAndView.setViewName("redirect:/users/{id}/wallets");
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/wallets/{id2}/deleteWallet", method = RequestMethod.POST)
    public ModelAndView deleteWallet(@SuppressWarnings("unused") @PathVariable("id") int id,
                                     @PathVariable("id2") int id2) {
        walletService.delete(walletService.findById(id2));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/users/{id}/wallets");
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/wallets/addWallet", method = RequestMethod.GET)
    public ModelAndView addWalletPage(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("walletPages/addWallet");
        modelAndView.addObject("id", id);
        modelAndView.addObject("currencies", currencyService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/wallets/addWallet", method = RequestMethod.POST)
    public ModelAndView addWallet(@PathVariable("id") int id,
                                  @ModelAttribute("wallet") Currency currency) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/users/{id}/wallets");
        currency = currencyService.findByName(currency.getName());
        User user = userService.findById(id);
        walletService.save(new Wallet(user, currency));
        return modelAndView;
    }


    @RequestMapping(value = "/myWallets", method = RequestMethod.GET)
    public ModelAndView walletsPage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("walletPages/wallets");
        String username;
        username = authentication.getName();
        modelAndView.addObject("currURL", "myWallets" );
        modelAndView.addObject("id", userService.findByLogin(username).getId() );
        modelAndView.addObject("wallets", walletService.
                findAllByUser(userService.findByLogin(username)) );
        return modelAndView;
    }

}
