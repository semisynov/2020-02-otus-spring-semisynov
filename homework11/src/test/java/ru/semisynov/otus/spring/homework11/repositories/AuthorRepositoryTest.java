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
import ru.semisynov.otus.spring.homework11.model.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Repository для работы с авторами книг ")
@DataMongoTest
@Import({ApplicationTestConfig.class, MongockConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private static final long EXPECTED_AUTHORS_COUNT = 5L;
    private static final String EXPECTED_AUTHOR_NAME = "ТестАвтор0";
    private static final String EXPECTED_ID = "0";

    @DisplayName("возвращает общее количество всех авторов")
    @Test
    void shouldReturnExpectedAuthorsCount() {
        StepVerifier
                .create(authorRepository.count())
                .assertNext(c -> assertEquals(EXPECTED_AUTHORS_COUNT, c))
                .expectComplete()
                .verify();
    }

    @DisplayName("возвращает список всех авторов")
    @Test
    public void shouldReturnExpectedAuthorsList() {
        StepVerifier
                .create(authorRepository.findAll())
                .assertNext(a -> assertEquals("ТестАвтор0", a.getName()))
                .assertNext(a -> assertEquals("ТестАвтор1", a.getName()))
                .assertNext(a -> assertEquals("ТестАвтор2", a.getName()))
                .assertNext(a -> assertEquals("ТестАвтор3", a.getName()))
                .assertNext(a -> assertEquals("ТестАвтор4", a.getName()))
                .expectComplete()
                .verify();
    }

    @DisplayName("возвращает заданного автора по его id")
    @Test
    public void shouldReturnExpectedAuthorById() {
        StepVerifier
                .create(authorRepository.findById(EXPECTED_ID))
                .assertNext(a -> assertEquals(EXPECTED_AUTHOR_NAME, a.getName()))
                .expectComplete()
                .verify();
    }

    @DisplayName("добавляет автора в БД")
    @Test
    public void shouldInsertAuthor() {
        StepVerifier
                .create(authorRepository.save(new Author("Тест")))
                .assertNext(a -> assertNotNull(a.getId()))
                .expectComplete()
                .verify();
    }

    @DisplayName("удаляет автора из БД")
    @Test
    void shouldDeleteAuthor() {
        StepVerifier
                .create(authorRepository.findById("0"))
                .expectNextCount(1)
                .then(() -> authorRepository.deleteById("0"))
                .then(() -> authorRepository.findById("0"))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }
}