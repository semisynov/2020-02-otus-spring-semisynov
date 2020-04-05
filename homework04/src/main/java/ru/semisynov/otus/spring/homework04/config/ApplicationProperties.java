package ru.semisynov.otus.spring.homework04.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private String locale;
    private String result;
    private String csvFileName;
}
