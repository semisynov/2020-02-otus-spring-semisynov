package ru.semisynov.otus.spring.homework09.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.semisynov.otus.spring.homework09.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework09.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework09.model.Author;
import ru.semisynov.otus.spring.homework09.model.Book;
import ru.semisynov.otus.spring.homework09.model.Genre;
import ru.semisynov.otus.spring.homework09.repositories.AuthorRepository;
import ru.semisynov.otus.spring.homework09.repositories.BookRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Класс AuthorServiceImplTest ")
class AuthorServiceImplTest {

    @Configuration
    static class TestConfig {

        @Bean
        public AuthorService authorService(AuthorRepository authorRepository, BookRepository bookRepository) {
            return new AuthorServiceImpl(authorRepository, bookRepository);
        }
    }

    private static final long EXPECTED_ID = 1L;
    private static final String EXPECTED_NAME = "Test";
    private static final Author EXPECTED_ENTITY = new Author(EXPECTED_ID, EXPECTED_NAME);

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    @Test
    @DisplayName("возвращает заданного автора по его id")
    void shouldReturnExpectedAuthorById() {
        when(authorRepository.findById(EXPECTED_ID)).thenReturn(Optional.of(EXPECTED_ENTITY));
        Author result = authorService.findAuthorById(EXPECTED_ID);

        assertThat(result).isEqualToComparingFieldByField(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("возвращает ошибку поиска автора по его id")
    void shouldReturnItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> authorService.findAuthorById(2L));
    }

    @Test
    @DisplayName("сохраняет автора в БД")
    void shouldSaveAuthor() {
        Author testAuthor = new Author(EXPECTED_NAME);
        when(authorRepository.save(any())).thenReturn(testAuthor);
        Author result = authorService.saveAuthor(testAuthor);

        assertThat(result).isEqualToComparingFieldByField(testAuthor);
    }

    @Test
    @DisplayName("возвращает всех авторов")
    void shouldReturnAllAuthors() {
        List<Author> authors = List.of(new Author("Test1"), new Author("Test2"), new Author("Test3"));
        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> result = authorService.findAllAuthors();

        assertThat(result).isNotNull().hasSize(authors.size())
                .allMatch(a -> !a.getName().equals(""));
    }

    @Test
    @DisplayName("удаляет автора из БД")
    void shouldDeleteAuthor() {
        Author testAuthor = new Author(5L, EXPECTED_NAME);
        when(authorRepository.findById(5L)).thenReturn(Optional.of(testAuthor));
        when(bookRepository.findByAuthors(testAuthor)).thenReturn(Collections.emptyList());
        doNothing().when(authorRepository).delete(testAuthor);

        assertDoesNotThrow(() -> authorService.deleteAuthorById(5L));
    }

    @Test
    @DisplayName("не удаляет автора из БД")
    void shouldNotDeleteAuthor() {
        Author testAuthor = new Author(10L, EXPECTED_NAME);
        Genre testGenre = new Genre(10L, EXPECTED_NAME);
        Book testBook = new Book("Test", List.of(testAuthor), List.of(testGenre));
        when(bookRepository.findByAuthors(testAuthor)).thenReturn(List.of(testBook));
        when(authorRepository.findById(10L)).thenReturn(Optional.of(testAuthor));

        doNothing().when(authorRepository).delete(testAuthor);

        assertThrows(DataReferenceException.class, () -> authorService.deleteAuthorById(10L));
    }
}