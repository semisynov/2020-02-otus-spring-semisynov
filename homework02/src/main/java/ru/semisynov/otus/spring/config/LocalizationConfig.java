package ru.semisynov.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Configuration
public class LocalizationConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("quiz");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    @Bean
    public Locale currentLocale(@Value("${quiz.locale}") String localeName) {
        if (!StringUtils.isEmpty(localeName)) {
            Locale locale = new Locale(localeName);
            LocaleContextHolder.setLocale(locale);
        }
        return LocaleContextHolder.getLocale();
    }
}
