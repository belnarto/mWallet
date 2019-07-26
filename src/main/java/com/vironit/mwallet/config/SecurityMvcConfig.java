package com.vironit.mwallet.config;

import com.vironit.mwallet.config.exception.SecurityConfigurationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.filter.CharacterEncodingFilter;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Log4j2
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.vironit.mwallet")
@Order(2)
public class SecurityMvcConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * To make sure that the Spring Security session registry
     * is notified when the session is destroyed
     * <p>
     * https://www.baeldung.com/spring-security-session
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * By defining this bean we override role prefix for Spring Security.
     * By default prefix = "ROLE_"
     * But for this app it's not required, so let's set prefix to ""
     */
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    /**
     * In this method we configuring Authentication Manager
     * with user details service and password encoder
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws SecurityConfigurationException {
        try {
            auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        } catch (Exception e) {
            log.error("Security configuration error.", e);
            throw new SecurityConfigurationException("Security configuration error.", e);
        }
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws SecurityConfigurationException {
        try {
            configureSessionManagement(httpSecurity);
            configureEncodingFilter(httpSecurity, "UTF-8");

            authorizeRequestsPermittedAll(httpSecurity);
            authorizeRequestsWithSecurityLimitation(httpSecurity);  // used only for servlets

            httpSecurity.authorizeRequests().anyRequest().authenticated();

            configureLogin(httpSecurity);
            configureLogout(httpSecurity);
            configureRememberMe(httpSecurity);

            httpSecurity.exceptionHandling().accessDeniedPage("/403");
        } catch (Exception e) {
            log.error("Security configuration error.", e);
            throw new SecurityConfigurationException("Security configuration error.", e);
        }
    }

    /**
     * This method configure session management.
     * https://www.baeldung.com/spring-security-session
     */
    private void configureSessionManagement(HttpSecurity httpSecurity) throws SecurityConfigurationException {
        try {
            httpSecurity.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .sessionFixation().migrateSession() // what happens to an existing session when the user tries to authenticate again
                    .maximumSessions(1)
                    .expiredUrl("/login");
        } catch (Exception e) {
            log.error("Security configuration error.", e);
            throw new SecurityConfigurationException("Security configuration error.", e);
        }
    }

    /**
     * This method set encoding filter.
     * Required to correct parse JSP.
     */
    @SuppressWarnings("SameParameterValue")
    private void configureEncodingFilter(HttpSecurity httpSecurity, String encoding) {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding(encoding);
        filter.setForceEncoding(true);
        httpSecurity.addFilterBefore(filter, CsrfFilter.class);
    }

    /**
     * This method authorize requests to URLs
     * which ae permitted to all
     */
    private void authorizeRequestsPermittedAll(HttpSecurity httpSecurity)
            throws SecurityConfigurationException {
        try {
            httpSecurity.authorizeRequests()
                    .antMatchers("/",
                            "/main",
                            "/403",
                            "/users/addUser",
                            "/currencies"
                    ).permitAll();
        } catch (Exception e) {
            log.error("Security configuration error.", e);
            throw new SecurityConfigurationException("Security configuration error.", e);
        }
    }

    /**
     * This method authorize requests to URLs
     * which has security limitations.
     * <p>
     * To limit access for users with DEFAULT role
     * to URLs which are not belong to them
     * securityGuard class is used.
     * This class has methods to check is "id" belong to logged user.
     */
    private void authorizeRequestsWithSecurityLimitation(HttpSecurity httpSecurity)
            throws SecurityConfigurationException {
        try {
            httpSecurity.authorizeRequests()
                    .antMatchers("/currencies/**").access("hasRole('ADMIN')");
        } catch (Exception e) {
            log.error("Security configuration error.", e);
            throw new SecurityConfigurationException("Security configuration error.", e);
        }
    }

    /**
     * This method configure login form.
     */
    private void configureLogin(HttpSecurity httpSecurity) throws SecurityConfigurationException {
        try {
            httpSecurity
                    .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error=true")
                    .defaultSuccessUrl("/")
                    .permitAll();
        } catch (Exception e) {
            log.error("Security configuration error.", e);
            throw new SecurityConfigurationException("Security configuration error.", e);
        }
    }

    /**
     * This method configure logout form.
     */
    private void configureLogout(HttpSecurity httpSecurity) throws SecurityConfigurationException {
        try {
            httpSecurity
                    .logout()
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/login?logout=true")
                    .permitAll();
        } catch (Exception e) {
            log.error("Security configuration error.", e);
            throw new SecurityConfigurationException("Security configuration error.", e);
        }
    }

    /**
     * This method configure remember me functionality.
     * <p>
     * https://www.baeldung.com/spring-security-remember-me
     */
    private void configureRememberMe(HttpSecurity httpSecurity) throws SecurityConfigurationException {
        try {
            httpSecurity
                    .rememberMe() // activate
                    .tokenRepository(persistentTokenRepository) // add repository for tokens
                    .rememberMeParameter("remember-me") // cookie name
                    .key("uniqueAndSecretKey") // private value secret for the entire app and it will be used when generating the contents of the token
                    .tokenValiditySeconds(24 * 60 * 60); // cookie will be valid for 1 day
        } catch (Exception e) {
            log.error("Security configuration error.", e);
            throw new SecurityConfigurationException("Security configuration error.", e);
        }
    }

}
