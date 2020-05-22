package ru.semisynov.otus.spring.homework09.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.semisynov.otus.spring.homework09.model.Book;
import ru.semisynov.otus.spring.homework09.model.Comment;
import ru.semisynov.otus.spring.homework09.repositories.BookRepository;
import ru.semisynov.otus.spring.homework09.repositories.CommentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Класс CommentServiceImplTest ")
class CommentServiceImplTest {

    @Configuration
    static class TestConfig {

        @Bean
        public CommentService commentService(CommentRepository commentRepository, BookRepository bookRepository) {
            return new CommentServiceImpl(commentRepository, bookRepository);
        }
    }

    private static final long EXPECTED_BOOK_ID = 1L;
    private static final String EXPECTED_TEXT = "Test";
    private static Comment EXPECTED_ENTITY;
    private static Book EXPECTED_BOOK;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        EXPECTED_BOOK = new Book();
        EXPECTED_ENTITY = new Comment();
        EXPECTED_ENTITY.setText(EXPECTED_TEXT);
    }

    @Test
    @DisplayName("создает новый комментарий")
    void shouldAddComment() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(EXPECTED_BOOK));
        when(commentRepository.save(any())).thenReturn(EXPECTED_ENTITY);

        Comment comment = commentService.addComment(EXPECTED_ENTITY, EXPECTED_BOOK_ID);
        assertThat(comment).isNotNull().matches(c -> c.getText().equals(EXPECTED_TEXT));
    }
}