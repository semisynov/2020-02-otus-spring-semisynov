package ru.semisynov.otus.spring.homework03.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Question")
class QuestionTest {
    @DisplayName("корректно создается конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        Question question = new Question("Вопрос", "Правильный ответ");

        assertAll("question",
                () -> assertEquals("Вопрос", question.getQuestion()),
                () -> assertEquals("Правильный ответ", question.getRightAnswer())
        );
    }
}