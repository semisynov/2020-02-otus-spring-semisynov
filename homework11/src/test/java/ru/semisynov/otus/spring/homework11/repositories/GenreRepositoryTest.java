package ru.semisynov.otus.spring.homework11.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import reactor.test.StepVerifier;
import ru.semisynov.otus.spring.homework11.config.ApplicationTestConfig;
import ru.semisynov.otus.spring.homework11.config.MongockConfig;
import ru.semisynov.otus.spring.homework11.model.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Repository для работы с жанрами книг ")
@DataMongoTest
@Import({ApplicationTestConfig.class, MongockConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    private static final long EXPECTED_GENRES_COUNT = 5L;
    private static final String EXPECTED_GENRE_TITLE = "ТестЖанр0";
    private static final String EXPECTED_ID = "0";

    @DisplayName("возвращает общее количество всех жанров")
    @Test
    void shouldReturnExpectedGenresCount() {
        StepVerifier
                .create(genreRepository.count())
                .assertNext(c -> assertEquals(EXPECTED_GENRES_COUNT, c))
                .expectComplete()
                .verify();
    }

    @DisplayName("возвращает список всех жанров")
    @Test
    public void shouldReturnExpectedAuthorsList() {
        StepVerifier
                .create(genreRepository.findAll())
                .assertNext(g -> assertEquals("ТестЖанр0", g.getTitle()))
                .assertNext(g -> assertEquals("ТестЖанр1", g.getTitle()))
                .assertNext(g -> assertEquals("ТестЖанр2", g.getTitle()))
                .assertNext(g -> assertEquals("ТестЖанр3", g.getTitle()))
                .assertNext(g -> assertEquals("ТестЖанр4", g.getTitle()))
                .expectComplete()
                .verify();
    }

    @DisplayName("возвращает заданный жанр по его id")
    @Test
    public void shouldReturnExpectedGenreById() {
        StepVerifier
                .create(genreRepository.findById(EXPECTED_ID))
                .assertNext(g -> assertEquals(EXPECTED_GENRE_TITLE, g.getTitle()))
                .expectComplete()
                .verify();
    }

    @DisplayName("добавляет жанр в БД")
    @Test
    public void shouldInsertGenre() {
        StepVerifier
                .create(genreRepository.save(new Genre("Тест")))
                .assertNext(g -> assertNotNull(g.getId()))
                .expectComplete()
                .verify();
    }

    @DisplayName("удаляет жанр из БД")
    @Test
    void shouldDeleteGenre() {
        StepVerifier
                .create(genreRepository.findById("0"))
                .expectNextCount(1)
                .then(() -> genreRepository.deleteById("0"))
                .then(() -> genreRepository.findById("0"))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }
}