package ru.semisynov.otus.spring.homework06.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.semisynov.otus.spring.homework06.errors.BadParameterException;
import ru.semisynov.otus.spring.homework06.services.CommentService;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("Класс CommentController ")
class CommentControllerTest {

    @Configuration
    static class TestConfig {

        @Bean
        public CommentController commentController(CommentService commentService) {
            return new CommentController(commentService);
        }
    }

    private static final String BAD_ID = "a";

    @MockBean
    private CommentService commentService;

    @Autowired
    private CommentController commentController;

    @Test
    void getCommentByIdBadParameterException() {
        assertThrows(BadParameterException.class, () -> commentController.getCommentById(BAD_ID));
    }

    @Test
    void deleteCommentByIdBadParameterException() {
        assertThrows(BadParameterException.class, () -> commentController.getCommentById(BAD_ID));
    }

    @Test
    void getBookCommentByIdBadParameterException() {
        assertThrows(BadParameterException.class, () -> commentController.getBookCommentsList(BAD_ID));
    }
}