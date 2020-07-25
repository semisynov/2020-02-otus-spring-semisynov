package ru.semisynov.otus.spring.homework14.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("spring.data.mongodb")
public class MongockConfig {
    private String host;
    private int port;
    private String database;

    private static final String CHANGELOG_PACKAGE = "ru.semisynov.otus.spring.homework14.changelogs";

    @Bean
    public Mongock mongock(MongockConfig mongockConfig, MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, mongockConfig.getDatabase(), CHANGELOG_PACKAGE)
                .build();
    }
}
