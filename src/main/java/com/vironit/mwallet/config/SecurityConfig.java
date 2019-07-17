package com.vironit.mwallet.config;

import com.vironit.mwallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.vironit.mwallet.services.UserService;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.filter.CharacterEncodingFilter;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.vironit.mwallet")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserDetailsService userDetailsService;

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
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        configureSessionManagement(httpSecurity);
        configureEncodingFilter(httpSecurity, "UTF-8");
        authorizeRequestsPermittedAll(httpSecurity);
        authorizeRequestsWithSecurityLimitation(httpSecurity);
        httpSecurity.authorizeRequests().anyRequest().authenticated();
        configureLogin(httpSecurity);
        configureLogout(httpSecurity);
        httpSecurity.exceptionHandling().accessDeniedPage("/403");
    }

    /**
     * This method configure session management.
     * https://www.baeldung.com/spring-security-session
     */
    private void configureSessionManagement(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/login")
                .sessionFixation().migrateSession() // what happens to an existing session when the user tries to authenticate again
                .maximumSessions(1)
                .expiredUrl("/login");
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
    private void authorizeRequestsPermittedAll(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/",
                        "/main",
                        "/403",
                        "/users/addUser",
                        "/currencies"
                ).permitAll();
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
    private void authorizeRequestsWithSecurityLimitation(HttpSecurity httpSecurity) throws Exception {
        //noinspection ELValidationInJSP,SpringElInspection
        httpSecurity.authorizeRequests()

                .antMatchers("/users/{userId}/wallets",
                        "/users/{userId}/wallets/addWallet")
                .access("@securityService.checkUserId(authentication,#userId) or hasRole('ADMIN')")

                .antMatchers("/users/{userId}/wallets/{walletId}/**")
                .access("(@securityService.checkUserId(authentication,#userId) and @securityService.checkWalletId(authentication,#walletId))" +
                        " or hasRole('ADMIN')")

                .antMatchers("/users/{userId}/updateUser",
                        "/users/{userId}/deleteUser",
                        "/users/{userId}")
                .access("@securityService.checkUserId(authentication,#userId) or hasRole('ADMIN')")

                .antMatchers("/users/**",
                        "/currencies/**")
                .access("hasRole('ADMIN')");
    }

    /**
     * This method configure login form.
     */
    private void configureLogin(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/")
                .permitAll();
    }

    /**
     * This method configure logout form.
     */
    private void configureLogout(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .logout()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login?logout=true")
                .permitAll();
    }

}
