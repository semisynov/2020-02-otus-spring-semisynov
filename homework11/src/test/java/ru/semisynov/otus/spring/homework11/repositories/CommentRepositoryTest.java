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
import ru.semisynov.otus.spring.homework11.model.Book;
import ru.semisynov.otus.spring.homework11.model.Comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Repository для работы с комментариями для книг ")
@DataMongoTest
@Import({ApplicationTestConfig.class, MongockConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final long EXPECTED_COMMENTS_COUNT = 6L;
    private static final String EXPECTED_TEXT = "ТестКоммент0";
    private static final String FIRST_BOOK_ID = "0";

    @DisplayName("возвращает общее количество всех комментариев")
    @Test
    void shouldReturnExpectedCommentsCount() {
        StepVerifier
                .create(commentRepository.count())
                .assertNext(c -> assertEquals(EXPECTED_COMMENTS_COUNT, c))
                .expectComplete()
                .verify();
    }

    @DisplayName("возвращает список всех комментариев")
    @Test
    void shouldReturnExpectedCommentsList() {
        StepVerifier
                .create(commentRepository.findAll())
                .assertNext(c -> assertEquals("ТестКоммент0", c.getText()))
                .assertNext(c -> assertEquals("ТестКоммент1", c.getText()))
                .assertNext(c -> assertEquals("ТестКоммент2", c.getText()))
                .assertNext(c -> assertEquals("ТестКоммент3", c.getText()))
                .assertNext(c -> assertEquals("ТестКоммент4", c.getText()))
                .assertNext(c -> assertEquals("ТестКоммент5", c.getText()))
                .expectComplete()
                .verify();
    }

    @DisplayName("возвращает список всех комментариев по книге")
    @Test
    void shouldReturnExpectedBookCommentsList() {
        StepVerifier
                .create(commentRepository.findAllByBookId(FIRST_BOOK_ID))
                .assertNext(c -> assertEquals("ТестКоммент0", c.getText()))
                .expectComplete()
                .verify();
    }

    @DisplayName("добавляет комментарий для книги в БД")
    @Test
    void shouldInsertComment() {
        Book book = mongoTemplate.findById("0", Book.class);
        Comment comment = new Comment(book, EXPECTED_TEXT);

        StepVerifier
                .create(commentRepository.save(comment))
                .assertNext(a -> assertNotNull(a.getId()))
                .expectComplete()
                .verify();
    }

    @DisplayName("удаляет комментарий из БД")
    @Test
    void shouldDeleteComment() {
        StepVerifier
                .create(commentRepository.findById("5"))
                .expectNextCount(1)
                .then(() -> commentRepository.deleteById("5"))
                .then(() -> commentRepository.findById("5"))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @DisplayName("удаляет комментарии по книге из БД")
    @Test
    void shouldDeleteBookComment() {
        Book book = mongoTemplate.findById("0", Book.class);

        StepVerifier
                .create(commentRepository.findAllByBookId(book.getId()))
                .expectNextCount(1)
                .then(() -> commentRepository.deleteAllByBook(book))
                .then(() -> commentRepository.findAllByBookId(book.getId()))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }
}