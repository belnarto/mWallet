package com.vironit.mwallet.service.impl;

import com.vironit.mwallet.model.entity.Role;
import com.vironit.mwallet.model.entity.User;
import com.vironit.mwallet.model.entity.Wallet;
import com.vironit.mwallet.service.JwtTokenService;
import com.vironit.mwallet.service.SecurityService;
import com.vironit.mwallet.service.UserService;
import com.vironit.mwallet.service.WalletService;
import com.vironit.mwallet.service.exception.AuthServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Log4j2
@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public boolean checkUserId(Authentication authentication, int userId) {
        String login = authentication.getName();
        User user = userService.findByLogin(login);
        return user != null && user.getId() == userId;
    }

    @Override
    public boolean checkWalletId(Authentication authentication, int walletId) {
        String login = authentication.getName();
        User user = userService.findByLogin(login);
        return user.getWallets().stream()
                .map(Wallet::getId)
                .anyMatch(walletId1 -> walletId1.equals(walletId));
    }

    @Override
    public String signIn(String username, String password) throws AuthServiceException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            List<Role> roles = new ArrayList<>();
            roles.add(userService.findByLogin(username).getRole());
            return jwtTokenService.createToken(username, roles);
        } catch (AuthenticationException e) {
            log.debug("Invalid username/password supplied", e);
            throw new AuthServiceException("Invalid username/password supplied");
        }
    }
}