package ru.semisynov.otus.spring.homework03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import ru.semisynov.otus.spring.homework03.config.ApplicationProperties;
import ru.semisynov.otus.spring.homework03.services.Quiz;

@SpringBootApplication
@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        Quiz quiz = context.getBean(Quiz.class);
        quiz.startQuiz();
    }
}
