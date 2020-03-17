package ru.semisynov.otus.spring.homework03.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.semisynov.otus.spring.homework03.config.ApplicationConfig;
import ru.semisynov.otus.spring.homework03.config.ApplicationProperties;
import ru.semisynov.otus.spring.homework03.domain.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Класс QuestionDao ")
class QuestionDaoImplTest {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private ApplicationConfig localizationConfig;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private MessageSource messageSource;

    @Test
    @DisplayName("корректно парсятся все строки")
    void questionListShouldCorrectParseCsv() {
        List<Question> questions = questionDao.questionList();
        String fileName = messageSource.getMessage("csv.file", null, localizationConfig.currentLocale(applicationProperties));
        Resource resource = new ClassPathResource(fileName);

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