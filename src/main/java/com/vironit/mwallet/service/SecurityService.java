package com.vironit.mwallet.service;

import com.vironit.mwallet.service.exception.AuthServiceException;
import org.springframework.security.core.Authentication;

@SuppressWarnings("unused")
public interface SecurityService {

    boolean checkUserId(Authentication authentication, int userId);

    boolean checkWalletId(Authentication authentication, int walletId);

    boolean checkTransactionId(Authentication authentication, int transactionId);

    String signIn(String username, String password) throws AuthServiceException;
}
