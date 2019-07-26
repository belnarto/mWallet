package com.vironit.mwallet.config;

import com.vironit.mwallet.config.exception.SecurityConfigurationException;
import com.vironit.mwallet.services.JwtTokenProvider;
import com.vironit.mwallet.utils.JwtTokenFilter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Log4j2
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "com.vironit.mwallet")
@Order(1)
public class SecurityRestConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws SecurityConfigurationException {
        try {
            return super.authenticationManagerBean();
        } catch (Exception e) {
            log.error("Security configuration error.", e);
            throw new SecurityConfigurationException("Security configuration error.", e);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws SecurityConfigurationException {
        try {
            http.antMatcher("/api/**")
                    .httpBasic().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/v1/signin").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/signup").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        } catch (Exception e) {
            log.error("Security configuration error.", e);
            throw new SecurityConfigurationException("Security configuration error.", e);
        }
    }

}
