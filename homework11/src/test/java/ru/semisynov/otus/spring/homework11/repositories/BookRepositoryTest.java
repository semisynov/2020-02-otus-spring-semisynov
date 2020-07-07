package ru.semisynov.otus.spring.homework11.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import reactor.test.StepVerifier;
import ru.semisynov.otus.spring.homework11.config.ApplicationTestConfig;
import ru.semisynov.otus.spring.homework11.config.MongockConfig;
import ru.semisynov.otus.spring.homework11.model.Author;
import ru.semisynov.otus.spring.homework11.model.Book;
import ru.semisynov.otus.spring.homework11.model.Genre;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Repository для работы с книгами ")
@DataMongoTest
@Import({ApplicationTestConfig.class, MongockConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final long EXPECTED_BOOKS_COUNT = 5L;
    private static final String EXPECTED_BOOKS_TITLE = "ТестКнига0";
    private static final String EXPECTED_ID = "0";

    @DisplayName("возвращает общее количество всех книг")
    @Test
    void shouldReturnExpectedBooksCount() {
        StepVerifier
                .create(bookRepository.count())
                .assertNext(c -> assertEquals(EXPECTED_BOOKS_COUNT, c))
                .expectComplete()
                .verify();
    }

    @DisplayName("возвращает список всех книг")
    @Test
    public void shouldReturnExpectedBooksList() {
        StepVerifier
                .create(bookRepository.findAll())
                .assertNext(b -> assertEquals("ТестКнига0", b.getTitle()))
                .assertNext(b -> assertEquals("ТестКнига1", b.getTitle()))
                .assertNext(b -> assertEquals("ТестКнига2", b.getTitle()))
                .assertNext(b -> assertEquals("ТестКнига3", b.getTitle()))
                .assertNext(b -> assertEquals("ТестКнига4", b.getTitle()))
                .expectComplete()
                .verify();
    }

    @DisplayName("возвращает заданную книгу по её id")
    @Test
    public void shouldReturnExpectedBookById() {
        StepVerifier
                .create(bookRepository.findById(EXPECTED_ID))
                .assertNext(b -> assertEquals(EXPECTED_BOOKS_TITLE, b.getTitle()))
                .expectComplete()
                .verify();
    }

    @DisplayName("добавляет книгу в БД")
    @Test
    public void shouldInsertBook() {
        StepVerifier
                .create(bookRepository.save(new Book("Тест", Collections.emptyList(), Collections.emptyList())))
                .assertNext(a -> assertNotNull(a.getId()))
                .expectComplete()
                .verify();
    }

    @DisplayName("удаляет книгу из БД")
    @Test
    void shouldDeleteBook() {
        StepVerifier
                .create(bookRepository.findById("0"))
                .expectNextCount(1)
                .then(() -> bookRepository.deleteById("0"))
                .then(() -> bookRepository.findById("0"))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @DisplayName("возвращает список всех книг по автору")
    @Test
    public void shouldReturnExpectedBooksListByAuthor() {
        Author author = mongoTemplate.findById("0", Author.class);

        StepVerifier
                .create(bookRepository.findByAuthors(author))
                .assertNext(b -> assertEquals("ТестКнига0", b.getTitle()))
                .expectComplete()
                .verify();
    }

    @DisplayName("возвращает список всех книг по жанру")
    @Test
    public void shouldReturnExpectedBooksListByGenre() {
        Genre genre = mongoTemplate.findById("0", Genre.class);

        StepVerifier
                .create(bookRepository.findByGenres(genre))
                .assertNext(b -> assertEquals("ТестКнига0", b.getTitle()))
                .expectComplete()
                .verify();
    }
}