package ru.semisynov.otus.spring.homework06.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.semisynov.otus.spring.homework06.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework06.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework06.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Dao для работы с авторами книг ")
@JdbcTest
@Import(AuthorRepositoryImpl.class)
class AuthorDaoJdbcTest {

    private static final int EXPECTED_AUTHORS_COUNT = 3;
    private static final String EXPECTED_AUTHOR_NAME_1 = "AnyAuthor1";
    private static final long EXPECTED_BOOK_ID = 3L;
    private static final int EXPECTED_BOOK_AUTHOR_COUNT = 2;
    private static final String TEXT_NOT_FOUND = "Author not found";
    private static final long NEW_ID = 4L;

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("возвращает общее количество всех авторов")
    @Test
    void shouldReturnExpectedAuthorsCount() {
        long count = authorRepository.count();
        assertThat(count).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("возвращает заданного автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author author = authorRepository.findById(1L).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        assertEquals(author.getName(), EXPECTED_AUTHOR_NAME_1);
    }

    @DisplayName("возвращает список всех авторов")
    @Test
    void shouldReturnExpectedAuthorsList() {
        List<Author> authors = authorRepository.findAll();
        assertThat(authors).hasSize(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("добавляет автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author expected = new Author(NEW_ID, "TestAuthor");
        authorRepository.save(expected);
        Author actual = authorRepository.findById(expected.getId()).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        assertThat(expected).isEqualToComparingFieldByField(actual);
    }

    @DisplayName("удаляет автора из БД")
    @Test
    void shouldDeleteAuthor() {
        long countBefore = authorRepository.count();
        authorRepository.deleteById(3L);
        long countAfter = authorRepository.count();
        assertThat(countAfter).isEqualTo(--countBefore);
    }

    @DisplayName("не удаляет автора из БД есть связь")
    @Test
    void shouldNotDeleteAuthor() {
        assertThrows(DataReferenceException.class, () -> authorRepository.deleteById(1L));
    }

//    @DisplayName("возвращает список всех авторов по id книги")
//    @Test
//    void shouldReturnExpectedBookAuthorList() {
//        List<Author> bookAuthors = authorRepository.findByBookId(EXPECTED_BOOK_ID);
//        assertThat(bookAuthors).hasSize(EXPECTED_BOOK_AUTHOR_COUNT);
//    }
//
//    @DisplayName("возвращает список пустой список авторов по id книги")
//    @Test
//    void shouldReturnEmptyBookAuthorList() {
//        List<Author> bookAuthors = authorRepository.findByBookId(10L);
//        assertTrue(bookAuthors.isEmpty());
//    }

    @DisplayName("возвращает заданного автора по его имени")
    @Test
    void shouldReturnExpectedAuthorByName() {
        Author author = authorRepository.findByName(EXPECTED_AUTHOR_NAME_1).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        assertEquals(author.getName(), EXPECTED_AUTHOR_NAME_1);
    }
}