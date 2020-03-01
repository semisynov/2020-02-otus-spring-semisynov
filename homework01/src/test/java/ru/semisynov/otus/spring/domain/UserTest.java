package ru.semisynov.otus.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс User")
class UserTest {

    @DisplayName("корректно создается конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        User user = new User("Семисынов", "Илья");

        assertAll("user",
                () -> assertEquals("Семисынов", user.getLastName()),
                () -> assertEquals("Илья", user.getFirstName()),
                () -> assertEquals(0, user.getResult())
        );
    }

    @DisplayName("корректно увеличивается результат")
    @Test
    void shouldHaveCorrectIncreaseResult() {
        User user = new User("Семисынов", "Илья");
        int currentResult = user.getResult();
        user.increaseResult();
        assertEquals(++currentResult, user.getResult());
    }
}