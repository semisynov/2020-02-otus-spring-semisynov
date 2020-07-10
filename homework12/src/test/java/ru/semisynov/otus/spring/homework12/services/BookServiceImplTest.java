package ru.semisynov.otus.spring.homework12.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.semisynov.otus.spring.homework12.dto.BookEntry;
import ru.semisynov.otus.spring.homework12.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework12.model.Author;
import ru.semisynov.otus.spring.homework12.model.Book;
import ru.semisynov.otus.spring.homework12.model.Genre;
import ru.semisynov.otus.spring.homework12.repositories.BookRepository;

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
        public BookService bookService(BookRepository bookRepository) {
            return new BookServiceImpl(bookRepository);
        }
    }

    private static final long EXPECTED_ID = 1L;
    private static final String EXPECTED_TITLE = "Test";
    private static final List<Author> AUTHORS = List.of(new Author(1L, "Author"));
    private static final List<Genre> GENRES = List.of(new Genre(1L, "Genre"));
    private static final Book EXPECTED_ENTITY = new Book(EXPECTED_ID, EXPECTED_TITLE, AUTHORS, GENRES, Collections.emptyList());

    private static BookEntry bookEntry;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookEntry = new BookEntry() {
            @Override
            public long getId() {
                return 1L;
            }

            @Override
            public String getTitle() {
                return EXPECTED_TITLE;
            }

            @Override
            public String getAuthorsInfo() {
                return null;
            }

            @Override
            public String getGenresInfo() {
                return null;
            }
        };
    }

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
        Book testBook = new Book(10L, EXPECTED_TITLE, authorList, genresList, Collections.emptyList());

        when(bookRepository.save(any())).thenReturn(testBook);
        Book result = bookService.saveBook(testBook);

        assertThat(result).isEqualToComparingFieldByField(testBook);
    }

    @Test
    @DisplayName("возвращает все книги")
    void shouldReturnAllBooks() {
        when(bookRepository.findAllBooks()).thenReturn(List.of(bookEntry));

        List<BookEntry> result = bookService.findAllBooks();
        Assertions.assertThat(result).isNotNull().hasSize(1)
                .allMatch(b -> b.getTitle().equals(bookEntry.getTitle()));
    }

    @Test
    @DisplayName("удаляет книгу из БД")
    void shouldDeleteAuthor() {
        when(bookRepository.findById(EXPECTED_ID)).thenReturn(Optional.of(EXPECTED_ENTITY));
        doNothing().when(bookRepository).delete(EXPECTED_ENTITY);

        assertDoesNotThrow(() -> bookService.deleteBookById(EXPECTED_ID));
    }

    @Test
    @DisplayName("удаляет книгу, нет в БД")
    void shouldNotDeleteAuthor() {
        doNothing().when(bookRepository).delete(any());

        assertThrows(ItemNotFoundException.class, () -> bookService.deleteBookById(100L));
    }
}