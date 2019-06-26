package com.vironit.mWallet.config;

import com.vironit.mWallet.converters.StringToRoleConverter;
import com.vironit.mWallet.dao.*;
import com.vironit.mWallet.dao.impl.CurrencyDaoImpl;
import com.vironit.mWallet.dao.impl.RoleDaoImpl;
import com.vironit.mWallet.dao.impl.UserDaoImpl;
import com.vironit.mWallet.dao.impl.WalletDaoImpl;
import com.vironit.mWallet.services.*;
import com.vironit.mWallet.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//import javax.validation.Validation;
//import javax.validation.Validator;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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

//    @Bean
//    CurrencyDao getCurrencyDao() {
//        return new CurrencyDaoImpl();
//    }

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
    public Validator getJavaValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        return validatorFactory.usingContext().getValidator();
    }

    @Bean
    ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Autowired
    RoleService roleService;

    @Override
    public void addFormatters (FormatterRegistry registry) {
        registry.addConverter(new StringToRoleConverter(roleService));
    }

}
