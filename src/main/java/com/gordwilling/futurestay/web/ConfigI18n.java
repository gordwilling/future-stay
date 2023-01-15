package com.gordwilling.futurestay.web;

import jakarta.validation.Validator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class ConfigI18n {

    @Bean
    public MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:lang/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(Locale.US);
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactory(MessageSource messageSource) {
        try (var localValidatorFactory = new LocalValidatorFactoryBean()) {
            localValidatorFactory.setValidationMessageSource(messageSource);
            return localValidatorFactory;
        }
    }

    @Bean
    public Validator validator(LocalValidatorFactoryBean validatorFactoryBean) {
        return validatorFactoryBean.getValidator();
    }
}
