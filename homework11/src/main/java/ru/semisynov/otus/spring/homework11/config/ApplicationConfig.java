package ru.semisynov.otus.spring.homework11.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    private static final String CHANGELOG_PACKAGE = "ru.semisynov.otus.spring.homework11.changelogs";

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Mongock mongock(MongockConfig mongoConfig, MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, mongoConfig.getDatabase(), CHANGELOG_PACKAGE)
                .build();
    }
}
