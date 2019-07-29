package com.vironit.mwallet.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vironit.mwallet.service.JwtTokenService;
import com.vironit.mwallet.util.exception.SecurityFilteringException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * https://medium.com/@xoor/jwt-authentication-service-44658409e12c
 * The JwtTokenFilter filter is applied to each API (/**) with exception of the signin token
 * endpoint (/users/signin) and singup endpoint (/users/signup).
 * This filter has the following responsibilities:
 * Check for access token in Authorization header. If Access token is found in the header,
 * delegate authentication to JwtTokenProvider otherwise throw authentication exception
 * Invokes success or failure strategies based on the outcome of authentication process
 * performed by JwtTokenProvider
 */
// We should use OncePerRequestFilter since we are doing a database call, there is no point in doing this more than once
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenService jwtTokenService;

    public JwtTokenFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenService.resolveToken(httpServletRequest);

        if (token == null &&
                (httpServletRequest.getRequestURI().equals("/api/v1/signin") ||
                httpServletRequest.getRequestURI().equals("/api/v1/signup") ||
                httpServletRequest.getRequestURI().equals("/api/v1/currencies"))) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        if (token == null) {
            PrintWriter out = httpServletResponse.getWriter();
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.flush();
            return;
        }
        try {
            if (jwtTokenService.validateToken(token)) {
                Authentication auth = jwtTokenService.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (SecurityFilteringException ex) {
            //this is very important, since it guarantees the user is not authenticated at all
            SecurityContextHolder.clearContext();

            PrintWriter out = httpServletResponse.getWriter();
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            out.print(Collections.singletonList("Security error: " + ex.getMessage()));
            out.flush();
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}