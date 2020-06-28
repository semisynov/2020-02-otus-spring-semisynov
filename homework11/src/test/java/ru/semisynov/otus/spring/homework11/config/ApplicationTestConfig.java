package ru.semisynov.otus.spring.homework11.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationTestConfig {

    private static final String CHANGELOG_PACKAGE = "ru.semisynov.otus.spring.homework11.changelogs.test";

    @Bean
    public Mongock mongock(MongockConfig mongoConfig, MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, mongoConfig.getDatabase(), CHANGELOG_PACKAGE)
                .build();
    }
}
