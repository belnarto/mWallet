package com.vironit.mWallet.config;

//import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.vironit.mWallet.services.UserService;

//@Configuration
//@EnableWebSecurity
@SuppressWarnings("unused")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/currencies/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/users").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/myWallets/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
                .and().formLogin();

    }
}
