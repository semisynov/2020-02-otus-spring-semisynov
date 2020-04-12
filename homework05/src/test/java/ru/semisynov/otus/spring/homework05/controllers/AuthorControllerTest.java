package ru.semisynov.otus.spring.homework05.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    private static final String BAD_ID = "a";

    @MockBean
    private AuthorService authorService;

    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        authorController = new AuthorController(authorService);
    }

    @Test
    void getAuthorByIdBadParameterException() {
        assertThrows(BadParameterException.class, () -> authorController.getAuthorById(BAD_ID));
    }

    @Test
    void deleteAuthorByIdBadParameterException() {
        assertThrows(BadParameterException.class, () -> authorController.getAuthorById(BAD_ID));
    }
}