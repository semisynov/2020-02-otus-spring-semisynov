package ru.semisynov.otus.spring.homework03.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Configuration
public class ApplicationConfig {

    @Bean
    public Locale currentLocale(ApplicationProperties applicationProperties) {
        if (!StringUtils.isEmpty(applicationProperties.getLocale())) {
            Locale locale = new Locale(applicationProperties.getLocale());
            LocaleContextHolder.setLocale(locale);
        }
        return LocaleContextHolder.getLocale();
    }
}
