package com.vironit.mwallet.config;

import com.vironit.mwallet.converters.StringToCurrencyConverter;
import com.vironit.mwallet.converters.StringToRoleConverter;
import com.vironit.mwallet.converters.StringToUserConverter;
import com.vironit.mwallet.converters.StringToWalletStatusConverter;
import com.vironit.mwallet.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.vironit.mwallet")
public class WebConfig implements WebMvcConfigurer {

    @Bean
    ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    // getting javax.validation validator
    // which uses validation annotations
    // in entities classes
    @Bean
    public Validator getJavaxValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        return validatorFactory.usingContext().getValidator();
    }

    // TODO check another solution for injection
    @Autowired
    RoleService roleService;

    @Autowired
    CurrencyService currencyService;

    @Override
    public void addFormatters (FormatterRegistry registry) {
        registry.addConverter(new StringToRoleConverter(roleService));
        registry.addConverter(new StringToCurrencyConverter(currencyService));
        registry.addConverter(new StringToUserConverter());
        registry.addConverter(new StringToWalletStatusConverter());
    }

}
