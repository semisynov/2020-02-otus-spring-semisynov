package ru.semisynov.otus.spring.homework08.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("spring.data.mongodb")
public class MongoConfig {
    private String host;
    private int port;
    private String database;
}
