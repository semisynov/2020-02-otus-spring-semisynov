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
import ru.semisynov.otus.spring.homework05.services.BookService;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("Класс BookController ")
class BookControllerTest {

    @Configuration
    static class TestConfig {

        @Bean
        public BookController bookController(BookService bookService) {
            return new BookController(bookService);
        }
    }

    private static final String BAD_ID = "a";

    @MockBean
    private BookService bookService;

    @Autowired
    private BookController bookController;

    @Test
    void getBookByIdBadParameterException() {
        assertThrows(BadParameterException.class, () -> bookController.getBookById(BAD_ID));
    }

    @Test
    void deleteAuthorByIdBadParameterException() {
        assertThrows(BadParameterException.class, () -> bookController.deleteBook(BAD_ID));
    }
}