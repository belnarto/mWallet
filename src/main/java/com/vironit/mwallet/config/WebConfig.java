package com.vironit.mwallet.config;

import com.vironit.mwallet.services.converters.StringToCurrencyConverter;
import com.vironit.mwallet.services.converters.StringToRoleConverter;
import com.vironit.mwallet.services.converters.UserIdToUserConverter;
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

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.vironit.mwallet")
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    StringToRoleConverter stringToRoleConverter;

    @Autowired
    StringToCurrencyConverter stringToCurrencyConverter;

    @Autowired
    UserIdToUserConverter userIdToUserConverter;

    @Autowired
    StringToWalletStatusConverter stringToWalletStatusConverter;

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

    /**
     * By overriding this method we add converting strategies
     * to convert String to target Class
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToRoleConverter);
        registry.addConverter(stringToCurrencyConverter);
        registry.addConverter(userIdToUserConverter);
        registry.addConverter(stringToWalletStatusConverter);
    }

}
