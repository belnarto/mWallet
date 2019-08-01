package com.vironit.mwallet.service.impl;

import com.vironit.mwallet.model.entity.MoneyTransferTransaction;
import com.vironit.mwallet.model.entity.PaymentTransaction;
import com.vironit.mwallet.model.entity.RechargeTransaction;
import com.vironit.mwallet.model.entity.Role;
import com.vironit.mwallet.model.entity.Transaction;
import com.vironit.mwallet.model.entity.User;
import com.vironit.mwallet.model.entity.Wallet;
import com.vironit.mwallet.service.JwtTokenService;
import com.vironit.mwallet.service.SecurityService;
import com.vironit.mwallet.service.TransactionService;
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
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Log4j2
@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;

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

    //TODO refactor
    @Override
    public boolean checkTransactionId(Authentication authentication, int transactionId) {
        Transaction transaction = transactionService.findById(transactionId);

        if (transaction == null) {
            return false;
        }

        String login = authentication.getName();
        User user = userService.findByLogin(login);
        List<Integer> walletsId = walletService.findAllByUser(user).stream()
                .map(Wallet::getId)
                .collect(Collectors.toList());

        boolean result = false;

        if (transaction instanceof RechargeTransaction) {
            int walletId = ((RechargeTransaction) transaction).getWalletId();
            result = walletsId.contains(walletId);
        } else if (transaction instanceof PaymentTransaction) {
            int walletId = ((PaymentTransaction) transaction).getWalletId();
            result = walletsId.contains(walletId);
        } else if (transaction instanceof MoneyTransferTransaction) {
            int walletId = ((MoneyTransferTransaction) transaction).getFromWalletId();
            result = walletsId.contains(walletId);
        }

        return result;
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