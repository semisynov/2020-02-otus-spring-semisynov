package ru.semisynov.otus.spring.homework08.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.semisynov.otus.spring.homework08.dto.CommentEntry;
import ru.semisynov.otus.spring.homework08.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework08.model.Author;
import ru.semisynov.otus.spring.homework08.model.Book;
import ru.semisynov.otus.spring.homework08.model.Comment;
import ru.semisynov.otus.spring.homework08.model.Genre;
import ru.semisynov.otus.spring.homework08.repositories.BookRepository;
import ru.semisynov.otus.spring.homework08.repositories.CommentRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("Класс CommentServiceImplTest ")
class CommentServiceImplTest {

    @Configuration
    static class TestConfig {

        @Bean
        public CommentService commentService(CommentRepository commentRepository, BookRepository bookRepository) {
            return new CommentServiceImpl(commentRepository, bookRepository);
        }
    }

    private static final long EXPECTED_COUNT = 1L;
    private static final String EXPECTED_ID = "10";
    private static final String EXPECTED_BOOK_ID = "12345";
    private static final String EXPECTED_TEXT = "Test";

    private static final String TEXT_COUNT = String.format("Comments in the database: %s", EXPECTED_COUNT);
    private static final String TEXT_EMPTY = "There are no comments in database";

    private static Comment EXPECTED_ENTITY;
    private static CommentEntry EXPECTED_ENTRY;

    @BeforeEach
    void setUp() {
        List<Author> authors = List.of(new Author("12345", "Test"));
        List<Genre> genres = List.of(new Genre("12345", "Test"));

        Book book = new Book(EXPECTED_BOOK_ID, "Test", authors, genres, Collections.emptyList());
        EXPECTED_ENTITY = new Comment(EXPECTED_ID, LocalDateTime.now(), EXPECTED_TEXT, book);

//        EXPECTED_ENTRY = new CommentEntry() {
//            @Override
//            public long getId() {
//                return 1L;
//            }
//
//            @Override
//            public String getText() {
//                return EXPECTED_TEXT;
//            }
//
//            @Override
//            public String getFullCommentInfo() {
//                return "Test";
//            }
//        };
    }

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("возвращает количество комментариев")
    void shouldReturnCommentsCount() {
        when(commentRepository.count()).thenReturn(EXPECTED_COUNT);
        String result = commentService.getCommentsCount();
        assertEquals(result, TEXT_COUNT);
    }

    @Test
    @DisplayName("возвращает количество комментариев, когда нет в БД")
    void shouldReturnEmptyCommentsCount() {
        when(commentRepository.count()).thenReturn(0L);
        String result = commentService.getCommentsCount();
        assertEquals(result, TEXT_EMPTY);
    }
//
//    @Test
//    @DisplayName("возвращает заданный комментарий по его id")
//    void shouldReturnExpectedCommentById() {
//        when(commentRepository.findCommentById(EXPECTED_ID)).thenReturn(Optional.of(EXPECTED_ENTRY));
//
//        String result = commentService.getCommentById(EXPECTED_ID);
//        assertThat(result).isNotBlank();
//    }
//
//    @Test
//    @DisplayName("возвращает ошибку поиска комментария по его id")
//    void shouldReturnItemNotFoundException() {
//        assertThrows(ItemNotFoundException.class, () -> commentService.getCommentById(10L));
//    }
//
//    @Test
//    @DisplayName("создает новый комментарий")
//    void shouldCreateComment() {
//        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(EXPECTED_ENTITY.getBook()));
//
//        String result = commentService.addComment(EXPECTED_TEXT, EXPECTED_BOOK_ID);
//        assertThat(result).isNotBlank();
//    }
//
//    @Test
//    @DisplayName("возвращает все комментарии")
//    void shouldReturnAllComments() {
//        List<CommentEntry> comments = List.of(EXPECTED_ENTRY);
//        when(commentRepository.findAllComments()).thenReturn(comments);
//
//        String result = commentService.getAllComments();
//        assertEquals(result, comments.stream().map(CommentEntry::getText).collect(Collectors.joining("\n")));
//    }
}