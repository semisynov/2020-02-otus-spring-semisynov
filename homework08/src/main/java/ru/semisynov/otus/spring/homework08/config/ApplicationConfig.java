package ru.semisynov.otus.spring.homework08.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    private static final String CHANGELOG_PACKAGE = "ru.semisynov.otus.spring.homework08.changelogs";

    @Bean
    public Mongock mongock(MongoConfig mongoConfig, MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, mongoConfig.getDatabase(), CHANGELOG_PACKAGE)
                .build();
    }
}
