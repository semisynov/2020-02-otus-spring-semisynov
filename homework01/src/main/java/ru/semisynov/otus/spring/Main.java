package ru.semisynov.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.semisynov.otus.spring.services.QuizImpl;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        QuizImpl quiz = context.getBean(QuizImpl.class);
        quiz.startQuiz();
    }
}
