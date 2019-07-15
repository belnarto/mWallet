package com.vironit.mwallet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.vironit.mwallet.services.UserService;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;


@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.vironit.mwallet")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    } //TODO length

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);


        //noinspection ELValidationInJSP,SpringElInspection
        http.addFilterBefore(filter, CsrfFilter.class)
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/main").permitAll()
                    .antMatchers("/test").permitAll()
                    .antMatchers("/403").permitAll()
                    .antMatchers("/users/addUser").permitAll()
                    .antMatchers("/currencies").permitAll()
                    .antMatchers("/currencies/**").access("hasRole('ADMIN')")
                    .antMatchers("/users/{id}/updateUser").access("@securityGuard.checkUserId(authentication,#id) or hasRole('ADMIN')")
                    .antMatchers("/users/{id}/deleteUser").access("@securityGuard.checkUserId(authentication,#id) or hasRole('ADMIN')")
                    .antMatchers("/users/**").access("hasRole('ADMIN')")
                    .antMatchers("/myWallets/**").access("hasRole('ADMIN') or hasRole('DEFAULT')")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error=true")
                    .permitAll()
                .and()
                    .logout()
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/login?logout=true")
                    .permitAll()
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/403");
    }

}
