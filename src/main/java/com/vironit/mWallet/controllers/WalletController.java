package com.vironit.mWallet.controllers;

import com.vironit.mWallet.models.Currency;
import com.vironit.mWallet.models.User;
import com.vironit.mWallet.models.Wallet;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.vironit.mWallet.services.CurrencyService;
import com.vironit.mWallet.services.UserService;
import com.vironit.mWallet.services.WalletService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

//@Controller
public class WalletController {

    private CurrencyService currencyService = new CurrencyService();

    @RequestMapping(value = "/users/{id}/com.vironit.mWallet.wallets", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView userWalletsPage(@PathVariable("id") int id, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("walletPages/com.vironit.mWallet.wallets");
        modelAndView.addObject("id", id );

        String username;
        String role;
            username = authentication.getName();
            role = authentication
                    .getAuthorities()
                    .stream()
                    .anyMatch( r-> r.getAuthority().equals("ROLE_ADMIN") )
                    ? "ROLE_ADMIN"
                    : "ROLE_USER";

        List<Wallet> wallets =  role.equals("ROLE_ADMIN")
                ? WalletService.findAllByUser(UserService.findById(id))
                : WalletService.findAllByUser(UserService.findByLogin(username));

        modelAndView.addObject("wallets", wallets );
        return modelAndView;
    }

    @RequestMapping(value = "/myWallets", method = RequestMethod.GET)
    public ModelAndView walletsPage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("walletPages/com.vironit.mWallet.wallets");
        String username;
        username = authentication.getName();
        modelAndView.addObject("id", UserService.findByLogin(username).getId() );
        modelAndView.addObject("wallets", WalletService.
                findAllByUser(UserService.findByLogin(username)) );
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/com.vironit.mWallet.wallets/addWallet", method = RequestMethod.GET)
    public ModelAndView addWalletPage(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("walletPages/addWallet");
        modelAndView.addObject("id", id );
        modelAndView.addObject("currencies", currencyService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/com.vironit.mWallet.wallets/addWallet", method = RequestMethod.POST)
    public ModelAndView addWallet(@PathVariable("id") int id,
                                  @ModelAttribute("wallet") Currency currency) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/com.vironit.mWallet.users/{id}/com.vironit.mWallet.wallets");
        currency = currencyService.findByName(currency.getName());
        User user = UserService.findById(id);
        WalletService.save(new Wallet(user,currency));
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/com.vironit.mWallet.wallets/editWallet/{id2}", method = RequestMethod.GET)
    public ModelAndView editWalletPage(@PathVariable("id") int id,
                                       @PathVariable("id2") int id2) {
        Wallet wallet = WalletService.findById(id2);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("walletPages/editWallet");
        modelAndView.addObject("id", id);
        modelAndView.addObject("wallet", wallet);
        modelAndView.addObject("currencies", currencyService.findAll()
                .stream()
                .filter( c -> !c.equals(wallet.getCurrency()))
                .collect(Collectors.toList()));
        return modelAndView;
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/users/{id}/com.vironit.mWallet.wallets/editWallet/{id2}", method = RequestMethod.POST)
    public ModelAndView editWallet(@PathVariable("id") int id,
                                   @PathVariable("id2") int id2,
                                   @ModelAttribute("wallet") Currency currencyNew) {
        ModelAndView modelAndView = new ModelAndView();
        currencyNew = currencyService.findByName(currencyNew.getName());
        Wallet wallet = WalletService.findById(id2);
        double balance = wallet.getBalance();
        Currency currencyOld = wallet.getCurrency();
        double targetValue = currencyOld.getRate() * balance; // to BYN
        targetValue = targetValue / currencyNew.getRate(); // to target Currency
        targetValue = new BigDecimal(targetValue)
                .setScale(3, RoundingMode.DOWN)
                .doubleValue();
        wallet.setBalance(targetValue);
        wallet.setCurrency(currencyNew);
        WalletService.update(wallet);
        modelAndView.setViewName("redirect:/com.vironit.mWallet.users/{id}/com.vironit.mWallet.wallets");
        return modelAndView;
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/users/{id}/com.vironit.mWallet.wallets/deleteWallet/{id2}", method = RequestMethod.GET)
    public ModelAndView deleteWallet(@PathVariable("id") int id,
                                         @PathVariable("id2") int id2) {
        WalletService.delete(WalletService.findById(id2));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/com.vironit.mWallet.users/{id}/com.vironit.mWallet.wallets");
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/com.vironit.mWallet.wallets/addBalance/{id2}", method = RequestMethod.GET)
    public ModelAndView addBalancePage(@PathVariable("id") int id,
                                       @PathVariable("id2") int id2) {
        Wallet wallet = WalletService.findById(id2);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("walletPages/addBalance");
        modelAndView.addObject("id", id);
        modelAndView.addObject("wallet", wallet);
        return modelAndView;
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/users/{id}/com.vironit.mWallet.wallets/addBalance/{id2}", method = RequestMethod.POST)
    public ModelAndView addBalance(@PathVariable("id") int id,
                                   @PathVariable("id2") int id2,
                                   @ModelAttribute("amountToAdd") double amountToAdd) {
        ModelAndView modelAndView = new ModelAndView();
        Wallet wallet = WalletService.findById(id2);
        WalletService.addBalance(wallet,amountToAdd);
        modelAndView.setViewName("redirect:/com.vironit.mWallet.users/{id}/com.vironit.mWallet.wallets");
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/com.vironit.mWallet.wallets/reduceBalance/{id2}", method = RequestMethod.GET)
    public ModelAndView reduceBalancePage(@PathVariable("id") int id,
                                          @PathVariable("id2") int id2) {
        Wallet wallet = WalletService.findById(id2);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("walletPages/reduceBalance");
        modelAndView.addObject("id", id);
        modelAndView.addObject("wallet", wallet);
        return modelAndView;
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/users/{id}/com.vironit.mWallet.wallets/reduceBalance/{id2}", method = RequestMethod.POST)
    public ModelAndView reduceBalance(@PathVariable("id") int id,
                                      @PathVariable("id2") int id2,
                                      @ModelAttribute("amountToReduce") double amountToReduce) {
        ModelAndView modelAndView = new ModelAndView();
        Wallet wallet = WalletService.findById(id2);
        WalletService.reduceBalance(wallet,amountToReduce);
        modelAndView.setViewName("redirect:/com.vironit.mWallet.users/{id}/com.vironit.mWallet.wallets");
        return modelAndView;
    }

    @RequestMapping(value = "/users/{id}/com.vironit.mWallet.wallets/transferMoney/{id2}", method = RequestMethod.GET)
    public ModelAndView transferMoneyPage(@PathVariable("id") int id,
                                          @PathVariable("id2") int id2) {
        Wallet wallet = WalletService.findById(id2);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("walletPages/transferMoney");
        modelAndView.addObject("id", id);
        modelAndView.addObject("wallet", wallet);
        return modelAndView;
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/users/{id}/com.vironit.mWallet.wallets/transferMoney/{id2}", method = RequestMethod.POST)
    public ModelAndView transferMoney(@PathVariable("id") int id,
                                      @PathVariable("id2") int id2,
                                      @ModelAttribute("amountToTransfer") double amountToTransfer,
                                      @ModelAttribute("targetWallet") int targetWallet) {
        ModelAndView modelAndView = new ModelAndView();
        Wallet wallet = WalletService.findById(id2);
        Wallet target_Wallet = WalletService.findById(targetWallet);
        WalletService.transferMoney(wallet,target_Wallet,amountToTransfer);
        modelAndView.setViewName("redirect:/com.vironit.mWallet.users/{id}/com.vironit.mWallet.wallets");
        return modelAndView;
    }

}
