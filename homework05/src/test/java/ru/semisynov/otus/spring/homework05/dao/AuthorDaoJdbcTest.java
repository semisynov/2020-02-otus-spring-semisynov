package ru.semisynov.otus.spring.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.semisynov.otus.spring.homework05.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework05.errors.ReferenceException;
import ru.semisynov.otus.spring.homework05.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с авторами книг ")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    private static final int EXPECTED_AUTHORS_COUNT = 3;
    private static final String EXPECTED_AUTHOR_NAME_1 = "AnyAuthor1";
    private static final long EXPECTED_BOOK_ID = 3L;
    private static final int EXPECTED_BOOK_AUTHOR_COUNT = 2;
    private static final String TEXT_NOT_FOUND = "Author not found";
    private static final long NEW_ID = 4L;

    @Autowired
    private AuthorDao authorDao;

    @DisplayName("возвращает общее количество всех авторов")
    @Test
    void shouldReturnExpectedAuthorsCount() {
        long count = authorDao.count();
        assertThat(count).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("возвращает заданного автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author author = authorDao.getById(1L).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        assertEquals(author.getName(), EXPECTED_AUTHOR_NAME_1);
    }

    @DisplayName("возвращает список всех авторов")
    @Test
    void shouldReturnExpectedAuthorsList() {
        List<Author> authors = authorDao.getAll();
        assertThat(authors).hasSize(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("добавляет автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author expected = new Author(NEW_ID, "TestAuthor");
        authorDao.insert(expected);
        Author actual = authorDao.getById(expected.getId()).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        assertThat(expected).isEqualToComparingFieldByField(actual);
    }

    @DisplayName("удаляет автора из БД")
    @Test
    void shouldDeleteAuthor() {
        long countBefore = authorDao.count();
        authorDao.deleteById(3L);
        long countAfter = authorDao.count();
        assertThat(countAfter).isEqualTo(--countBefore);
    }

    @DisplayName("не удаляет автора из БД есть связь")
    @Test
    void shouldNotDeleteAuthor() {
        assertThrows(ReferenceException.class, () -> authorDao.deleteById(1L));
    }

    @DisplayName("возвращает список всех авторов по id книги")
    @Test
    void shouldReturnExpectedBookAuthorList() {
        List<Author> bookAuthors = authorDao.getByBookId(EXPECTED_BOOK_ID);
        assertThat(bookAuthors).hasSize(EXPECTED_BOOK_AUTHOR_COUNT);
    }

    @DisplayName("возвращает список пустой список авторов по id книги")
    @Test
    void shouldReturnEmptyBookAuthorList() {
        List<Author> bookAuthors = authorDao.getByBookId(10L);
        assertTrue(bookAuthors.isEmpty());
    }

    @DisplayName("возвращает заданного автора по его имени")
    @Test
    void shouldReturnExpectedAuthorByName() {
        Author author = authorDao.getByName(EXPECTED_AUTHOR_NAME_1).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        assertEquals(author.getName(), EXPECTED_AUTHOR_NAME_1);
    }
}