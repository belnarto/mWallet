package com.vironit.mwallet.config;

import com.vironit.mwallet.services.converters.StringToCurrencyConverter;
import com.vironit.mwallet.services.converters.StringToRoleConverter;
import com.vironit.mwallet.services.converters.StringToUserConverter;
import com.vironit.mwallet.services.converters.StringToWalletStatusConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
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

    /**
     * https://habr.com/ru/post/438808/
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }

    // getting javax.validation validator
    // which uses validation annotations
    // in entities classes
    @Bean
    public Validator getJavaxValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        return validatorFactory.usingContext().getValidator();
    }


    @Autowired
    StringToRoleConverter stringToRoleConverter;

    @Autowired
    StringToCurrencyConverter stringToCurrencyConverter;

    @Autowired
    StringToUserConverter stringToUserConverter;

    @Autowired
    StringToWalletStatusConverter stringToWalletStatusConverter;

    /**
     * By overriding this method we add converting strategies
     * to convert String to target Class
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToRoleConverter);
        registry.addConverter(stringToCurrencyConverter);
        registry.addConverter(stringToUserConverter);
        registry.addConverter(stringToWalletStatusConverter);
    }

}
