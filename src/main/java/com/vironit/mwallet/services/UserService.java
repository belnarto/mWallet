package com.vironit.mwallet.services;

import com.vironit.mwallet.models.User;
import com.vironit.mwallet.services.exception.LoginAlreadyDefinedException;

import java.util.List;

public interface UserService {

    User findById(int id);

    User findByLogin(String login);

    void save(User user) throws LoginAlreadyDefinedException;

    void delete(User user);

    void update(User user);

    List<User> findAll();


}
