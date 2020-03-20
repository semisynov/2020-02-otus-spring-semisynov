package ru.semisynov.otus.spring.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.semisynov.otus.spring.Main;
import ru.semisynov.otus.spring.domain.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс QuestionDao")
class QuestionDaoImplTest {

    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    private List<Question> questions;
    private Resource resource;

    @BeforeEach
    void setUp() {
        context.register(Main.class);
        context.refresh();
        resource = new ClassPathResource("questions_ru_RU.csv");
    }

    @DisplayName("корректно парсятся все строки")
    @Test
    void shouldCorrectParseCsv() {
        QuestionDao questionDao = context.getBean(QuestionDao.class);
        questions = questionDao.questionList();
        int linesCount = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(resource.getFile()))) {
            while (bufferedReader.readLine() != null) {
                linesCount++;
            }
        } catch (IOException ignored) {
        }
        assertThat(questions).hasSize(linesCount);
    }
}