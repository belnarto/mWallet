package com.vironit.mwallet.services.converters;

import com.vironit.mwallet.models.dto.UserDto;
import com.vironit.mwallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class UserIdToUserConverter implements Converter<String, UserDto> {

    @Autowired
    private UserService userService;

    public UserDto convert(String source) {
        return userService.findById(Integer.parseInt(source));
    }
}
