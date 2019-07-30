package com.vironit.mwallet.service;

import com.vironit.mwallet.model.entity.User;
import com.vironit.mwallet.service.exception.LoginAlreadyDefinedException;

import java.util.List;

public interface UserService {

    User findById(int id);

    User findByLogin(String login);

    void save(User user) throws LoginAlreadyDefinedException;

    void delete(User user);

    void update(User user) throws LoginAlreadyDefinedException;

    List<User> findAll();

    List<User> findAllByNamePart(String namePart);

}
