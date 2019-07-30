package com.vironit.mwallet.util;

import com.vironit.mwallet.model.entity.Role;
import com.vironit.mwallet.service.JwtTokenService;
import com.vironit.mwallet.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://gist.github.com/akaramires/7577298
 * https://stackoverflow.com/questions/36286112/authenticationsuccesshandler-spring-security
 * https://stackoverflow.com/questions/23118014/add-a-cookie-during-the-spring-security-login
 *
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class JwtTokenHandler extends SavedRequestAwareAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private RoleService roleService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        List<Role> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(roleService::findByName)
                .collect(Collectors.toList());
        String token = jwtTokenService.createToken(authentication.getName(), roles);
        Cookie cookie = new Cookie("JwtToken", token);
        cookie.setMaxAge(28800);
        response.addCookie(cookie);
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
