package com.vironit.mwallet.services;

import com.vironit.mwallet.models.entity.Role;
import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.services.exception.LoginAlreadyDefinedException;
import com.vironit.mwallet.utils.exception.SecurityFilteringException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            List<Role> roles = new ArrayList<>();
            roles.add(userService.findByLogin(username).getRole());
            return jwtTokenProvider.createToken(username, roles);
        } catch (AuthenticationException e) {
            throw new SecurityFilteringException("Invalid username/password supplied",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signup(User user) {
        try {
            userService.save(user);
            List<Role> roles = new ArrayList<>();
            roles.add(user.getRole());
            return jwtTokenProvider.createToken(user.getLogin(), roles);
        } catch (LoginAlreadyDefinedException e) {
            throw new SecurityFilteringException("Username is already in use",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

}
