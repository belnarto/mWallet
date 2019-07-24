package com.vironit.mwallet.services.mapper;

import com.vironit.mwallet.models.dto.UserDto;
import com.vironit.mwallet.models.dto.UserRestDto;
import com.vironit.mwallet.models.dto.UserRestDtoWithoutPassword;
import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.services.RoleService;
import com.vironit.mwallet.services.UserService;
import com.vironit.mwallet.services.WalletService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "unused"})
@Component
public class UserMapper {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(User.class, UserRestDto.class)
                .addMappings(m -> m.skip(UserRestDto::setRoleId))
                .setPostConverter(toRestDtoConverter());
        mapper.createTypeMap(UserRestDto.class, User.class)
                .addMappings(m -> m.skip(User::setRole))
                .setPostConverter(toEntityConverter());

        mapper.createTypeMap(User.class, UserRestDtoWithoutPassword.class)
                .addMappings(m -> m.skip(UserRestDtoWithoutPassword::setRoleId))
                .setPostConverter(toRestDtoWithoutPasswordConverter());
    }

    private Converter<User, UserRestDto> toRestDtoConverter() {
        return context -> {
            User source = context.getSource();
            UserRestDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<UserRestDto, User> toEntityConverter() {
        return context -> {
            UserRestDto source = context.getSource();
            User destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<User, UserRestDtoWithoutPassword> toRestDtoWithoutPasswordConverter() {
        return context -> {
            User source = context.getSource();
            UserRestDtoWithoutPassword destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(UserRestDto source, User destination) {
        destination.setRole(roleService.findById(Integer.parseInt(source.getRoleId())));
        User currentUser = userService.findById(source.getId());
        if (currentUser != null) {
            destination.setWallets(currentUser.getWallets());
        }

    }

    private void mapSpecificFields(User source, UserRestDto destination) {
        destination.setRoleId(String.valueOf(source.getRole().getId()));
    }

    private void mapSpecificFields(User source, UserRestDtoWithoutPassword destination) {
        destination.setRoleId(String.valueOf(source.getRole().getId()));
    }

    public User toEntity(UserRestDto userRestDto) {
        return Objects.isNull(userRestDto) ? null : mapper.map(userRestDto, User.class);
    }

    public UserRestDto toRestDto(User userEntity) {
        return Objects.isNull(userEntity) ? null : mapper.map(userEntity, UserRestDto.class);
    }

    public UserRestDtoWithoutPassword toRestDtoWithoutPassword(User userEntity) {
        return Objects.isNull(userEntity) ? null : mapper.map(userEntity, UserRestDtoWithoutPassword.class);
    }

    public User toEntity(UserDto userDto) {
        return Objects.isNull(userDto) ? null : mapper.map(userDto, User.class);
    }

    public UserDto toDto(User userEntity) {
        return Objects.isNull(userEntity) ? null : mapper.map(userEntity, UserDto.class);
    }
}