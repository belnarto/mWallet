package com.vironit.mWallet.utils;

import com.vironit.mWallet.models.User;
import com.vironit.mWallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SecurityGuard {

    @Autowired
    private UserService userService;

    public boolean checkUserId(Authentication authentication, int id) {
        String login = authentication.getName();
        User user = userService.findByLogin(login);
        return user != null && user.getId() == id;
    }
}