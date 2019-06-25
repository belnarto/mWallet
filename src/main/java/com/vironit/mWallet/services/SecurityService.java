package com.vironit.mWallet.services;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String login, String password);
}