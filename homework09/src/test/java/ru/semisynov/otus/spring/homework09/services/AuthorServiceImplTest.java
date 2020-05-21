package ru.semisynov.otus.spring.homework09.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.semisynov.otus.spring.homework09.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework09.model.Author;
import ru.semisynov.otus.spring.homework09.repositories.AuthorRepository;
import ru.semisynov.otus.spring.homework09.repositories.BookRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;


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

    private static final long EXPECTED_COUNT = 1L;
    private static final long EXPECTED_ID = 1L;
    private static final String EXPECTED_NAME = "Test";
    private static final Author EXPECTED_ENTITY = new Author(EXPECTED_ID, EXPECTED_NAME);

    private static final String TEXT_EMPTY = "There are no authors in database";
    private static final String TEXT_COUNT = String.format("Authors in the database: %s", EXPECTED_COUNT);
    private static final String TEXT_BY_ID = String.format("Author(id=%s, name=%s)", EXPECTED_ID, EXPECTED_NAME);
    private static final String TEXT_NEW = "New author id: %s, name: %s";

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private AuthorRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    @Test
    @DisplayName("возвращает заданного автора по его id")
    void shouldReturnExpectedAuthorById() {
//        when(authorRepository.findById(EXPECTED_ID)).thenReturn(Optional.of(EXPECTED_ENTITY));
//        String result = authorService.findAuthorById(EXPECTED_ID);
//        assertEquals(result, TEXT_BY_ID);
    }

    @Test
    @DisplayName("возвращает ошибку поиска автора по его id")
    void shouldReturnItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> authorService.findAuthorById(2L));
    }

    @Test
    @DisplayName("создает нового автора")
    void shouldCreateAuthor() {
//        Author testAuthor = new Author(10L, EXPECTED_NAME);
//        when(authorRepository.save(any())).thenReturn(testAuthor);
//        String result = authorService.addAuthor(EXPECTED_NAME);
//        assertEquals(result, String.format(TEXT_NEW, 10L, EXPECTED_NAME));
    }

    @Test
    @DisplayName("возвращает всех авторов")
    void shouldReturnAllAuthors() {
//        List<Author> authors = List.of(new Author(0L, "Test1"), new Author(0L, "Test2"), new Author(0L, "Test3"));
//        when(authorRepository.findAll()).thenReturn(authors);
//
//        String result = authorService.findAllAuthors();
//        assertEquals(result, authors.stream().map(Author::toString).collect(Collectors.joining("\n")));
    }
}