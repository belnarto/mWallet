package com.vironit.mwallet.utils;

import com.vironit.mwallet.models.User;
import com.vironit.mwallet.services.UserService;
import com.vironit.mwallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SecurityGuard {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    public boolean checkUserId(Authentication authentication, int id) {
        String login = authentication.getName();
        User user = userService.findByLogin(login);
        return user != null && user.getId() == id;
    }

    public boolean checkWalletId(Authentication authentication, int walletId) {
        String login = authentication.getName();
        User user = userService.findByLogin(login);
        return user.getWallets().contains(walletService.findById(walletId));
    }
}