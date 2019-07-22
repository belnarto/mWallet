package com.vironit.mwallet.services.converters;

import com.vironit.mwallet.models.dto.UserDto;
import com.vironit.mwallet.services.UserService;
import com.vironit.mwallet.services.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class UserIdToUserConverter implements Converter<String, UserDto> {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public UserDto convert(String source) {
        return userMapper.toDto(userService.findById(Integer.parseInt(source)));
    }
}
