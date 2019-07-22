package com.vironit.mwallet.services.mapper;

import com.vironit.mwallet.models.dto.UserDto;
import com.vironit.mwallet.models.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class UserMapper {

    @Autowired
    private ModelMapper mapper;

    public User toEntity(UserDto userDto) {
        return Objects.isNull(userDto) ? null : mapper.map(userDto, User.class);
    }

    public UserDto toDto(User userEntity) {
        return Objects.isNull(userEntity) ? null : mapper.map(userEntity, UserDto.class);
    }
}