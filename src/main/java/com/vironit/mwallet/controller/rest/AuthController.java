package com.vironit.mwallet.controller.rest;

import com.vironit.mwallet.controller.rest.exception.UserRestControllerException;
import com.vironit.mwallet.controller.rest.exception.UserValidationErrorException;
import com.vironit.mwallet.model.dto.TokenDto;
import com.vironit.mwallet.model.dto.UserRestDto;
import com.vironit.mwallet.model.dto.UserRestDtoWithoutPassword;
import com.vironit.mwallet.model.entity.User;
import com.vironit.mwallet.service.JwtTokenService;
import com.vironit.mwallet.service.SecurityService;
import com.vironit.mwallet.service.UserService;
import com.vironit.mwallet.service.exception.AuthServiceException;
import com.vironit.mwallet.service.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
@Log4j2
@RequestMapping("/api/v1/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/signin")
    public ResponseEntity<TokenDto> signin(@RequestParam String username,
                                           @RequestParam String password) throws AuthServiceException {
        return new ResponseEntity<>(new TokenDto(securityService.signIn(username, password)),
                HttpStatus.OK);
    }

    @SuppressWarnings("Duplicates")
    @PostMapping(value = "/signup")
    public ResponseEntity<UserRestDtoWithoutPassword> createUser(@Valid @RequestBody UserRestDto userRestDto,
                                                                 BindingResult bindingResult)
            throws UserRestControllerException {

        if (bindingResult.hasErrors()) {
            throw new UserValidationErrorException(bindingResult.getAllErrors());
        }
        try {
            User user = userMapper.toEntity(userRestDto);
            userService.save(user);
            return new ResponseEntity<>(userMapper.toRestDtoWithoutPassword(user),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error during user saving", e);
            throw new UserRestControllerException("Error during user saving", e);
        }
    }

}
