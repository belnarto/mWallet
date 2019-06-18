package com.vironit.mWallet.config;

import com.vironit.mWallet.dao.*;
import com.vironit.mWallet.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.vironit.mWallet")
public class WebConfig implements WebMvcConfigurer {


    @Bean
    UserDao getUserDao() {
        return new UserDaoImpl();
    }

    @Bean
    WalletDao getWalletDao() {
        return new WalletDaoImpl();
    }

    @Bean
    CurrencyDao getCurrencyDao() {
        return new CurrencyDaoImpl();
    }

    @Bean
    RoleDao getRoleDao() {
        return new RoleDaoImpl();
    }



    @Bean
    UserService getUserService() {
        return new UserService();
    }

    @Bean
    WalletService getWalletService() {
        return new WalletService();
    }

    @Bean
    CurrencyService getCurrencyService() {
        return new CurrencyService();
    }

    @Bean
    RoleService getRoleService() {
        return new RoleService();
    }


    @Bean
    ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

}
