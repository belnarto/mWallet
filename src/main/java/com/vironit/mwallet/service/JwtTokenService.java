package com.vironit.mwallet.service;

import com.vironit.mwallet.model.entity.Role;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@SuppressWarnings("unused")
public interface JwtTokenService {

    String createToken(String username, List<Role> roles);

    Authentication getAuthentication(String token);

    String getUsername(String token);

    String resolveToken(HttpServletRequest req);

    boolean validateToken(String token);

}
