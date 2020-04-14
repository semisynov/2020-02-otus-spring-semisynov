package ru.semisynov.otus.spring.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.semisynov.otus.spring.homework05.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework05.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework05.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с жанрами книг ")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final int EXPECTED_GENRES_COUNT = 3;
    private static final String EXPECTED_GENRE_TITLE_1 = "AnyGenre1";
    private static final long EXPECTED_BOOK_ID = 3L;
    private static final int EXPECTED_BOOK_GENRES_COUNT = 2;
    private static final String TEXT_NOT_FOUND = "Genre not found";
    private static final long NEW_ID = 4L;

    @Autowired
    private GenreDao genreDao;

    @DisplayName("возвращает общее количество всех жанров")
    @Test
    void shouldReturnExpectedGenresCount() {
        long count = genreDao.count();
        assertThat(count).isEqualTo(EXPECTED_GENRES_COUNT);
    }

    @DisplayName("возвращает заданный жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre genre = genreDao.getById(1L).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        assertEquals(genre.getTitle(), EXPECTED_GENRE_TITLE_1);
    }

    @DisplayName("возвращает список всех авторов")
    @Test
    void shouldReturnExpectedGenresList() {
        List<Genre> genres = genreDao.getAll();
        assertThat(genres).hasSize(EXPECTED_GENRES_COUNT);
    }

    @DisplayName("добавляет жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre expected = new Genre(NEW_ID, "TestGenre");
        genreDao.insert(expected);
        Genre actual = genreDao.getById(expected.getId()).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        assertThat(expected).isEqualToComparingFieldByField(actual);
    }

    @DisplayName("удаляет жанр из БД")
    @Test
    void shouldDeleteGenre() {
        long countBefore = genreDao.count();
        genreDao.deleteById(3L);
        long countAfter = genreDao.count();
        assertThat(countAfter).isEqualTo(--countBefore);
    }

    @DisplayName("не удаляет жанр из БД есть связь")
    @Test
    void shouldNotDeleteGenre() {
        assertThrows(DataReferenceException.class, () -> genreDao.deleteById(2L));
    }

    @DisplayName("возвращает список всех жанров по id книги")
    @Test
    void shouldReturnExpectedBookGenresList() {
        List<Genre> bookGenres = genreDao.getByBookId(EXPECTED_BOOK_ID);
        assertThat(bookGenres).hasSize(EXPECTED_BOOK_GENRES_COUNT);
    }

    @DisplayName("возвращает список пустой список жанров по id книги")
    @Test
    void shouldReturnEmptyBookGenresList() {
        List<Genre> bookGenres = genreDao.getByBookId(10L);
        assertTrue(bookGenres.isEmpty());
    }

    @DisplayName("возвращает заданный жанр по его имени")
    @Test
    void shouldReturnExpectedGenreByName() {
        Genre genre = genreDao.getByTitle(EXPECTED_GENRE_TITLE_1).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        assertEquals(genre.getTitle(), EXPECTED_GENRE_TITLE_1);
    }
}