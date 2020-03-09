package ru.semisynov.otus.spring;

import org.springframework.context.annotation.*;
import ru.semisynov.otus.spring.config.LocalizationConfig;
import ru.semisynov.otus.spring.services.Quiz;

@PropertySource("classpath:application.properties")
@Import(LocalizationConfig.class)
@EnableAspectJAutoProxy
@ComponentScan
@Configuration
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        Quiz quiz = (Quiz) context.getBean("quiz");
        quiz.startQuiz();
    }
}
