package ru.semisynov.otus.spring.homework10.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.semisynov.otus.spring.homework10.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework10.model.Author;
import ru.semisynov.otus.spring.homework10.model.Book;
import ru.semisynov.otus.spring.homework10.model.Comment;
import ru.semisynov.otus.spring.homework10.model.Genre;
import ru.semisynov.otus.spring.homework10.repositories.BookRepository;
import ru.semisynov.otus.spring.homework10.repositories.CommentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Класс BookServiceImplTest ")
class BookServiceImplTest {

    @Configuration
    static class TestConfig {

        @Bean
        public BookService bookService(BookRepository bookRepository, CommentService commentService) {
            return new BookServiceImpl(bookRepository, commentService);
        }

        @Bean
        public CommentService commentService(CommentRepository commentRepository) {
            return new CommentServiceImpl(commentRepository);
        }
    }

    private static final long EXPECTED_ID = 1L;
    private static final String EXPECTED_TITLE = "Test";
    private static final List<Author> AUTHORS = List.of(new Author(1L, "Author"));
    private static final List<Genre> GENRES = List.of(new Genre(1L, "Genre"));
    private static final Book EXPECTED_ENTITY = new Book(EXPECTED_ID, EXPECTED_TITLE, AUTHORS, GENRES, Collections.emptyList());
    private static final Comment EXPECTED_COMMENT = new Comment(EXPECTED_ENTITY, "Test");

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("возвращает заданную книгу по её id")
    void shouldReturnExpectedBookById() {
        when(bookRepository.findById(EXPECTED_ID)).thenReturn(Optional.of(EXPECTED_ENTITY));
        Book result = bookService.findBookById(EXPECTED_ID);

        assertThat(result).isNotNull().matches(a -> a.getTitle().equals(EXPECTED_ENTITY.getTitle()));
    }

    @Test
    @DisplayName("возвращает ошибку поиска книги по её id")
    void shouldReturnItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> bookService.findBookById(10L));
    }

    @Test
    @DisplayName("создает новую книгу")
    void shouldCreateBook() {
        List<Author> authorList = List.of(new Author("Author"));
        List<Genre> genresList = List.of(new Genre("Genre"));
        Book testBook = new Book(EXPECTED_ID, EXPECTED_TITLE, authorList, genresList, Collections.emptyList());

        when(bookRepository.save(any())).thenReturn(testBook);
        Book result = bookService.saveBook(testBook);

        assertThat(result).isEqualToComparingFieldByField(testBook);
    }

    @Test
    @DisplayName("обновляет книгу в БД")
    void shouldUpdateBook() {
        List<Author> authorList = List.of(new Author("Author"));
        List<Genre> genresList = List.of(new Genre("Genre"));
        Book testBook = new Book(EXPECTED_ID, EXPECTED_TITLE, authorList, genresList, Collections.emptyList());

        when(bookRepository.findById(any())).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any())).thenReturn(testBook);
        Book result = bookService.updateBook(EXPECTED_ID, testBook);

        assertThat(result).isEqualToComparingFieldByField(testBook);
    }

    @Test
    @DisplayName("не обновляет книгу в БД")
    void shouldNotUpdateBook() {
        List<Author> authorList = List.of(new Author("Author"));
        List<Genre> genresList = List.of(new Genre("Genre"));
        Book testBook = new Book(EXPECTED_ID, EXPECTED_TITLE, authorList, genresList, Collections.emptyList());
        when(bookRepository.save(any())).thenReturn(testBook);

        assertThrows(ItemNotFoundException.class, () -> bookService.updateBook(EXPECTED_ID, testBook));
    }

    @Test
    @DisplayName("возвращает все книги")
    void shouldReturnAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(EXPECTED_ENTITY));

        List<Book> result = bookService.findAllBooks();
        Assertions.assertThat(result).isNotNull().hasSize(1)
                .allMatch(b -> b.getTitle().equals(EXPECTED_ENTITY.getTitle()));
    }

    @Test
    @DisplayName("удаляет книгу из БД")
    void shouldDeleteBook() {
        when(bookRepository.findById(EXPECTED_ID)).thenReturn(Optional.of(EXPECTED_ENTITY));
        doNothing().when(bookRepository).delete(EXPECTED_ENTITY);

        assertDoesNotThrow(() -> bookService.deleteBookById(EXPECTED_ID));
    }

    @Test
    @DisplayName("не удаляет книгу, нет в БД")
    void shouldNotDeleteBook() {
        doNothing().when(bookRepository).delete(any());

        assertThrows(ItemNotFoundException.class, () -> bookService.deleteBookById(100L));
    }

    @Test
    @DisplayName("создает комментарий для книги")
    void shouldCreateBookComment() {
        when(bookRepository.findById(EXPECTED_ID)).thenReturn(Optional.of(EXPECTED_ENTITY));
        when(commentRepository.save(any())).thenReturn(EXPECTED_COMMENT);

        assertDoesNotThrow(() -> bookService.addBookComment(EXPECTED_ID, "Test"));
    }

    @Test
    @DisplayName("не создает комментарий для книги, нет в БД")
    void shouldNotCreateBookComment() {
        when(commentRepository.save(any())).thenReturn(EXPECTED_COMMENT);

        assertThrows(ItemNotFoundException.class, () -> bookService.addBookComment(100L, "Test"));
    }

}