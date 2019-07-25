package com.vironit.mwallet.controllers;

import com.vironit.mwallet.models.dto.UserRestDto;
import com.vironit.mwallet.services.AuthService;
import com.vironit.mwallet.services.JwtTokenProvider;
import com.vironit.mwallet.services.UserService;
import com.vironit.mwallet.services.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/signin")
    public String signin(@RequestParam String username,
                         @RequestParam String password) {
        return authService.signin(username, password);
    }

    @PostMapping("/signup")
    public String signin(@RequestBody UserRestDto userRestDto) {
        return authService.signup(userMapper.toEntity(userRestDto));
    }

}
