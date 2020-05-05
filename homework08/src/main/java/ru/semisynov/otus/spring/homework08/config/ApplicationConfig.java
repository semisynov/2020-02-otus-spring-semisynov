package ru.semisynov.otus.spring.homework08.config;

import com.github.cloudyrock.mongock.*;
import com.mongodb.MongoClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

//@Configuration
public class ApplicationConfig {
////
//    private static final String CHANGELOG_PACKAGE = "ru.semisynov.otus.spring.homework08.changelogs";
//
//    @Bean
//    public Mongock mongock(MongoClient mongoClient) {
//        return new SpringMongockBuilder(mongoClient, "library", CHANGELOG_PACKAGE)
//                .build();
//    }

    /*
//
//  Mongock runner=  new MongockBuilder(mongoclient, "yourDbName", "com.package.to.be.scanned.for.changesets")
//      .setLockQuickConfig()
//      .setStartSystemVersion("1")
//      .setEndSystemVersion("2.5.5")
//      .build();
//         */
//    }
//    @Bean
//    public SpringMongock springMongock(MongoTemplate mongoTemplate, Environment springEnvironment) {
//        return new SpringMongockBuilder(mongoTemplate, CHANGELOG_PACKAGE)
//                .setSpringEnvironment(springEnvironment)
//                .setLockQuickConfig()
//                .build();
//    }

//    @Bean
//    public SpringBootMongock mongock(MongoTemplate mongoTemplate, ApplicationContext springContext) {
//        return new SpringBootMongockBuilder(mongoTemplate, CHANGELOG_PACKAGE)
//                .setApplicationContext(springContext)
//                .setLockQuickConfig()
//                .build();
//    }

//    @Bean
//    public Mongock mongock(MongoConfig mongoConfig, MongoClient mongoClient) {
//        return new SpringMongockBuilder(mongoClient, mongoConfig.getDatabase(), CHANGELOG_PACKAGE)
//                .build();
//    }
}
