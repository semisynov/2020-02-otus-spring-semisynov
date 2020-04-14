package ru.semisynov.otus.spring.homework05.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.semisynov.otus.spring.homework05.errors.BadParameterException;
import ru.semisynov.otus.spring.homework05.services.AuthorService;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("Класс AuthorController ")
class AuthorControllerTest {

    @Configuration
    static class TestConfig {

        @Bean
        public AuthorController authorController(AuthorService authorService) {
            return new AuthorController(authorService);
        }
    }

    private static final String BAD_ID = "a";

    @MockBean
    private AuthorService authorService;

    @Autowired
    private AuthorController authorController;

    @Test
    void getAuthorByIdBadParameterException() {
        assertThrows(BadParameterException.class, () -> authorController.getAuthorById(BAD_ID));
    }

    @Test
    void deleteAuthorByIdBadParameterException() {
        assertThrows(BadParameterException.class, () -> authorController.getAuthorById(BAD_ID));
    }
}