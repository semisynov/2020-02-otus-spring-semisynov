package ru.semisynov.otus.spring.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.semisynov.otus.spring.homework05.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework05.model.Author;
import ru.semisynov.otus.spring.homework05.model.Book;
import ru.semisynov.otus.spring.homework05.model.Genre;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Dao для работы с книгами ")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {
    private static final int EXPECTED_BOOKS_COUNT = 3;
    private static final String EXPECTED_GENRE_TITLE_1 = "AnyBook3";
    private static final long EXPECTED_BOOK_ID = 4L;
    private static final String TEXT_NOT_FOUND = "Book not found";

    @Autowired
    private BookDao bookDao;

    @DisplayName("возвращает общее количество всех книг")
    @Test
    void shouldReturnExpectedBooksCount() {
        long count = bookDao.count();
        assertThat(count).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("возвращает заданную книгу по её id")
    @Test
    void shouldReturnExpectedBookById() {
        Book book = bookDao.getById(3L).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));

        assertEquals(book.getTitle(), EXPECTED_GENRE_TITLE_1);
    }

    @DisplayName("возвращает список всех книг")
    @Test
    void shouldReturnExpectedBooksList() {
        List<Book> books = bookDao.getAll();
        assertThat(books).hasSize(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("добавляет книгу в БД")
    @Test
    void shouldInsertBook() {
        List<Author> authors = Collections.emptyList();
        List<Genre> genres = Collections.emptyList();
        Book expected = new Book(EXPECTED_BOOK_ID, "TestBook", authors, genres);
        bookDao.insert(expected);
        Book actual = bookDao.getById(expected.getId()).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        assertThat(expected).isEqualToComparingFieldByField(actual);
    }

    @DisplayName("удаляет книгу из БД")
    @Test
    void shouldDeleteBook() {
        long countBefore = bookDao.count();
        bookDao.deleteById(2L);
        long countAfter = bookDao.count();
        assertThat(countAfter).isEqualTo(--countBefore);
    }
}