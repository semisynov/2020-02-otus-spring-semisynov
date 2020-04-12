package ru.semisynov.otus.spring.homework05.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.semisynov.otus.spring.homework05.dao.AuthorDao;
import ru.semisynov.otus.spring.homework05.dao.BookDao;
import ru.semisynov.otus.spring.homework05.dao.GenreDao;
import ru.semisynov.otus.spring.homework05.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework05.model.Author;
import ru.semisynov.otus.spring.homework05.model.Book;
import ru.semisynov.otus.spring.homework05.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        public BookService bookService(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
            return new BookServiceImpl(bookDao, authorDao, genreDao);
        }
    }

    private static final long EXPECTED_COUNT = 1L;
    private static final long EXPECTED_ID = 1L;
    private static final String EXPECTED_TITLE = "Test";
    private static final List<Author> AUTHORS = List.of(new Author(1L, "Author"));
    private static final List<Genre> GENRES = List.of(new Genre(1L, "Genre"));
    private static final Book EXPECTED_ENTITY = new Book(EXPECTED_ID, EXPECTED_TITLE, AUTHORS, GENRES);

    private static final String TEXT_EMPTY = "There are no books in database";
    private static final String TEXT_COUNT = String.format("Books in the database: %s", EXPECTED_COUNT);
    private static final String TEXT_BY_ID = String.format("Book(id=%s, title=%s, authors=[Author(id=1, name=Author)], genres=[Genre(id=1, title=Genre)])", EXPECTED_ID, EXPECTED_TITLE);
    private static final String TEXT_NEW = "New book id: 2, name: Test2";

    @MockBean
    private BookDao bookDao;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private GenreDao genreDao;

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("возвращает количество книг")
    void shouldReturnBooksCount() {
        when(bookDao.count()).thenReturn(EXPECTED_COUNT);
        String result = bookService.getBooksCount();
        assertEquals(result, TEXT_COUNT);
    }

    @Test
    @DisplayName("возвращает количество авторов, когда нет в БД")
    void shouldReturnEmptyBooksCount() {
        when(bookDao.count()).thenReturn(0L);
        String result = bookService.getBooksCount();
        assertEquals(result, TEXT_EMPTY);
    }

    @Test
    @DisplayName("возвращает заданную книгу по её id")
    void shouldReturnExpectedBookById() {
        when(bookDao.getById(EXPECTED_ID)).thenReturn(Optional.of(EXPECTED_ENTITY));
        String result = bookService.getBookById(EXPECTED_ID);
        assertEquals(result, TEXT_BY_ID);
    }

    @Test
    @DisplayName("возвращает ошибку поиска книги по её id")
    void shouldReturnItemNotFoundException() {
        assertThrows(ItemNotFoundException.class, () -> bookService.getBookById(10L));
    }

    @Test
    @DisplayName("создает новую книгу")
    void shouldCreateBook() {
        when(bookDao.insert(any())).thenReturn(2L);
        when(authorDao.getByName(any())).thenReturn(Optional.of(new Author(1L, "Author")));
        when(genreDao.getByTitle(any())).thenReturn(Optional.of(new Genre(1L, "Genre")));
        String authors = "Author";
        String genres = "Genre";
        String result = bookService.createBook("Test2", authors, genres);
        assertEquals(result, TEXT_NEW);
    }

    @Test
    @DisplayName("не создает новую книгу, если нет автора")
    void shouldNotCreateBookWithoutAuthor() {
        when(bookDao.insert(any())).thenReturn(2L);
        when(authorDao.getByName(any())).thenReturn(Optional.empty());
        when(genreDao.getByTitle(any())).thenReturn(Optional.of(new Genre(1L, "Genre")));
        String authors = "Author";
        String genres = "Genre";

        assertThrows(ItemNotFoundException.class, () -> bookService.createBook("Test2", authors, genres));
    }

    @Test
    @DisplayName("не создает новую книгу, если нет жанра")
    void shouldNotCreateBookWithoutGenre() {
        when(bookDao.insert(any())).thenReturn(2L);
        when(authorDao.getByName(any())).thenReturn(Optional.of(new Author(1L, "Author")));
        when(genreDao.getByTitle(any())).thenReturn(Optional.empty());
        String authors = "Author";
        String genres = "Genre";

        assertThrows(ItemNotFoundException.class, () -> bookService.createBook("Test2", authors, genres));
    }

    @Test
    @DisplayName("возвращает все книги")
    void shouldReturnAllAuthors() {
        when(bookDao.getAll()).thenReturn(List.of(EXPECTED_ENTITY));

        String result = bookService.getAllBooks();
        assertEquals(result, List.of(EXPECTED_ENTITY).stream().map(Book::toString).collect(Collectors.joining("\n")));
    }
}