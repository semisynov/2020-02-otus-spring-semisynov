package ru.semisynov.otus.spring.homework06.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.semisynov.otus.spring.homework06.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework06.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework06.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Dao для работы с жанрами книг ")
@JdbcTest
@Import(GenreRepositoryImpl.class)
class GenreDaoJdbcTest {

    private static final int EXPECTED_GENRES_COUNT = 3;
    private static final String EXPECTED_GENRE_TITLE_1 = "AnyGenre1";
    private static final long EXPECTED_BOOK_ID = 3L;
    private static final int EXPECTED_BOOK_GENRES_COUNT = 2;
    private static final String TEXT_NOT_FOUND = "Genre not found";
    private static final long NEW_ID = 4L;

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("возвращает общее количество всех жанров")
    @Test
    void shouldReturnExpectedGenresCount() {
        long count = genreRepository.count();
        assertThat(count).isEqualTo(EXPECTED_GENRES_COUNT);
    }

    @DisplayName("возвращает заданный жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre genre = genreRepository.findById(1L).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        assertEquals(genre.getTitle(), EXPECTED_GENRE_TITLE_1);
    }

    @DisplayName("возвращает список всех авторов")
    @Test
    void shouldReturnExpectedGenresList() {
        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).hasSize(EXPECTED_GENRES_COUNT);
    }

    @DisplayName("добавляет жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre expected = new Genre(NEW_ID, "TestGenre");
        genreRepository.save(expected);
        Genre actual = genreRepository.findById(expected.getId()).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        assertThat(expected).isEqualToComparingFieldByField(actual);
    }

    @DisplayName("удаляет жанр из БД")
    @Test
    void shouldDeleteGenre() {
        long countBefore = genreRepository.count();
        genreRepository.deleteById(3L);
        long countAfter = genreRepository.count();
        assertThat(countAfter).isEqualTo(--countBefore);
    }

    @DisplayName("не удаляет жанр из БД есть связь")
    @Test
    void shouldNotDeleteGenre() {
        assertThrows(DataReferenceException.class, () -> genreRepository.deleteById(2L));
    }

    @DisplayName("возвращает заданный жанр по его имени")
    @Test
    void shouldReturnExpectedGenreByName() {
        Genre genre = genreRepository.getByTitle(EXPECTED_GENRE_TITLE_1).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        assertEquals(genre.getTitle(), EXPECTED_GENRE_TITLE_1);
    }
}