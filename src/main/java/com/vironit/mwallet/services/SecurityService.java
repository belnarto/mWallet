package com.vironit.mwallet.services;

import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.models.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class SecurityService {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    public boolean checkUserId(Authentication authentication, int userId) {
        String login = authentication.getName();
        User user = userService.findByLogin(login);
        return user != null && user.getId() == userId;
    }

    public boolean checkWalletId(Authentication authentication, int walletId) {
        String login = authentication.getName();
        User user = userService.findByLogin(login);
        Wallet wallet = walletService.findById(walletId);
        return user.getWallets().stream()
                .map(wallet1 -> wallet.getId())
                .anyMatch(walletId1 -> walletId1.equals(walletId));
    }
}