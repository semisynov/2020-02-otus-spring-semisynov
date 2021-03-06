package ru.semisynov.otus.spring.homework08.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.semisynov.otus.spring.homework08.dto.BookEntry;
import ru.semisynov.otus.spring.homework08.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework08.model.Author;
import ru.semisynov.otus.spring.homework08.model.Book;
import ru.semisynov.otus.spring.homework08.model.Genre;
import ru.semisynov.otus.spring.homework08.repositories.AuthorRepository;
import ru.semisynov.otus.spring.homework08.repositories.BookRepository;
import ru.semisynov.otus.spring.homework08.repositories.GenreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("Класс BookServiceImplTest ")
class BookServiceImplTest {

    @Configuration
    static class TestConfig {

        @Bean
        public BookService bookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
            return new BookServiceImpl(bookRepository, authorRepository, genreRepository);
        }
    }

    private static final long EXPECTED_COUNT = 1L;
    private static final String EXPECTED_ID = "12345";
    private static final String EXPECTED_TITLE = "Test";

    private static final String TEXT_EMPTY = "There are no books in database";
    private static final String TEXT_COUNT = String.format("Books in the database: %s", EXPECTED_COUNT);

    private static BookEntry bookEntry;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookEntry = new BookEntry() {
            @Override
            public String getId() {
                return "1";
            }

            @Override
            public String getTitle() {
                return EXPECTED_TITLE;
            }

            @Override
            public String getFullBookInfo() {
                return EXPECTED_TITLE;
            }
        };
    }

    @Test
    @DisplayName("возвращает количество книг")
    void shouldReturnBooksCount() {
        when(bookRepository.count()).thenReturn(EXPECTED_COUNT);
        String result = bookService.getBooksCount();
        assertEquals(result, TEXT_COUNT);
    }

    @Test
    @DisplayName("возвращает количество авторов, когда нет в БД")
    void shouldReturnEmptyBooksCount() {
        when(bookRepository.count()).thenReturn(0L);
        String result = bookService.getBooksCount();
        assertEquals(result, TEXT_EMPTY);
    }

    @Test
    @DisplayName("возвращает заданную книгу по её id")
    void shouldReturnExpectedBookById() {
        when(bookRepository.findBookById(EXPECTED_ID)).thenReturn(Optional.of(bookEntry));
        String result = bookService.findBookById(EXPECTED_ID);
        assertThat(result).isEqualTo(EXPECTED_TITLE);
    }

    @Test
    @DisplayName("возвращает ошибку поиска книги по её id")
    void shouldReturnItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> bookService.findBookById("10000"));
    }

    @Test
    @DisplayName("создает новую книгу")
    void shouldCreateBook() {
        List<Author> authorList = List.of(new Author("1", "Author"));
        List<Genre> genresList = List.of(new Genre("1", "Genre"));
        Book book = new Book("10", EXPECTED_TITLE, authorList, genresList, Collections.emptyList());

        when(bookRepository.save(any())).thenReturn(book);
        when(authorRepository.findByNameIgnoreCase(any())).thenReturn(Optional.of(new Author("1", "Author")));
        when(genreRepository.findByTitleIgnoreCase(any())).thenReturn(Optional.of(new Genre("1", "Genre")));

        String authors = "Author";
        String genres = "Genre";
        String result = bookService.addBook(EXPECTED_TITLE, authors, genres);
        assertThat(result).isNotBlank();
    }

    @Test
    @DisplayName("не создает новую книгу, если нет автора")
    void shouldNotCreateBookWithoutAuthor() {
        List<Author> authorList = List.of(new Author("1", "Author"));
        List<Genre> genresList = List.of(new Genre("1", "Genre"));
        Book book = new Book("10", EXPECTED_TITLE, authorList, genresList, Collections.emptyList());

        when(bookRepository.save(any())).thenReturn(book);
        when(authorRepository.findByNameIgnoreCase(any())).thenReturn(Optional.empty());
        when(genreRepository.findByTitleIgnoreCase(any())).thenReturn(Optional.of(new Genre("1", "Genre")));
        String authors = "Author";
        String genres = "Genre";

        assertThrows(ItemNotFoundException.class, () -> bookService.addBook("Test2", authors, genres));
    }

    @Test
    @DisplayName("не создает новую книгу, если нет жанра")
    void shouldNotCreateBookWithoutGenre() {
        List<Author> authorList = List.of(new Author("1", "Author"));
        List<Genre> genresList = List.of(new Genre("1", "Genre"));
        Book book = new Book("10", EXPECTED_TITLE, authorList, genresList, Collections.emptyList());

        when(bookRepository.save(any())).thenReturn(book);
        when(authorRepository.findByNameIgnoreCase(any())).thenReturn(Optional.of(new Author("1", "Author")));
        when(genreRepository.findByTitleIgnoreCase(any())).thenReturn(Optional.empty());
        String authors = "Author";
        String genres = "Genre";

        assertThrows(ItemNotFoundException.class, () -> bookService.addBook("Test2", authors, genres));
    }

    @Test
    @DisplayName("возвращает все книги")
    void shouldReturnAllAuthors() {
        when(bookRepository.findAllBooks()).thenReturn(List.of(bookEntry));

        String result = bookService.findAllBooks();
        assertEquals(result, EXPECTED_TITLE);
    }
}