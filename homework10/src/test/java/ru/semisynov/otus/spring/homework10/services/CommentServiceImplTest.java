package ru.semisynov.otus.spring.homework10.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.semisynov.otus.spring.homework10.model.Book;
import ru.semisynov.otus.spring.homework10.model.Comment;
import ru.semisynov.otus.spring.homework10.repositories.CommentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Класс CommentServiceImplTest ")
class CommentServiceImplTest {

    @Configuration
    static class TestConfig {

        @Bean
        public CommentService commentService(CommentRepository commentRepository) {
            return new CommentServiceImpl(commentRepository);
        }
    }

    private static final String EXPECTED_TEXT = "Test";
    private static final Book EXPECTED_BOOK = new Book();
    private static final Comment EXPECTED_ENTITY = new Comment(EXPECTED_BOOK, EXPECTED_TEXT);

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("создает новый комментарий")
    void shouldAddComment() {
        when(commentRepository.save(any())).thenReturn(EXPECTED_ENTITY);

        Comment comment = commentService.addComment(EXPECTED_BOOK, EXPECTED_TEXT);
        assertThat(comment).isNotNull().matches(c -> c.getText().equals(EXPECTED_TEXT));
    }
}