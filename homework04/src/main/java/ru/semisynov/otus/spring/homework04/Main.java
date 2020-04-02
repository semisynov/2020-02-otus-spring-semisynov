package ru.semisynov.otus.spring.homework04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.semisynov.otus.spring.homework04.config.ApplicationProperties;

@SpringBootApplication
@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
