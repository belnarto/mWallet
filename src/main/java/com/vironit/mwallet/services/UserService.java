package com.vironit.mwallet.services;

import com.vironit.mwallet.models.dto.UserDto;
import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.services.exception.LoginAlreadyDefinedException;

import java.util.List;

public interface UserService {

    UserDto findById(int id);

    UserDto findByLogin(String login);

    void save(UserDto userDto) throws LoginAlreadyDefinedException;

    void delete(UserDto userDto);

    void update(UserDto userDto);

    List<UserDto> findAll();

}
