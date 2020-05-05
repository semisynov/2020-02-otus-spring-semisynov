package ru.semisynov.otus.spring.homework08.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.semisynov.otus.spring.homework08.config.ApplicationTestConfig;
import ru.semisynov.otus.spring.homework08.config.MongoConfig;
import ru.semisynov.otus.spring.homework08.dto.CommentEntry;
import ru.semisynov.otus.spring.homework08.model.Book;
import ru.semisynov.otus.spring.homework08.model.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с комментариями для книг ")
@DataMongoTest
@Import({ApplicationTestConfig.class, MongoConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CommentRepositoryTest {

    private static final int EXPECTED_COMMENTS_COUNT = 5;
    private static final String AUTHOR_NAME_PREFIX = "ТестКоммент";
    // private static final String TEXT_NOT_FOUND = "Коммент not found";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("возвращает общее количество всех комментариев")
    @Test
    void shouldReturnExpectedCommentsCount() {
        long count = commentRepository.count();
        assertThat(count).isEqualTo(EXPECTED_COMMENTS_COUNT);
    }

    @DisplayName("возвращает заданный комментарий по его id")
    @Test
    void shouldReturnExpectedCommentById() {
        List<Comment> comments = mongoTemplate.findAll(Comment.class);
        Comment comment = comments.get(0);

        Optional<Comment> optionalComment = commentRepository.findById(comment.getId());

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(comment.getId()));
        Comment expectedComment = mongoTemplate.findOne(query, Comment.class);

        assertThat(optionalComment).isPresent().get().matches(c -> {
            assert expectedComment != null;
            return c.getText().equals(expectedComment.getText());
        });
    }

    @DisplayName("возвращает заданный комментарий по его id dto")
    @Test
    void shouldReturnExpectedCommentEntryById() {
        List<Comment> comments = mongoTemplate.findAll(Comment.class);
        Comment comment = comments.get(0);

        Optional<CommentEntry> optionalCommentEntry = commentRepository.findCommentById(comment.getId());

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(comment.getId()));
        Comment expectedComment = mongoTemplate.findOne(query, Comment.class);

        assertThat(optionalCommentEntry).isPresent().get().matches(c -> {
            assert expectedComment != null;
            return c.getText().equals(expectedComment.getText());
        });
    }

    @DisplayName("возвращает список всех комментариев")
    @Test
    void shouldReturnExpectedCommentsList() {
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).isNotNull().hasSize(EXPECTED_COMMENTS_COUNT)
                .allMatch(c -> !c.getText().equals(""));
    }

    @DisplayName("возвращает список всех книг dto")
    @Test
    void shouldReturnExpectedCommentsEntryList() {
        List<CommentEntry> comments = commentRepository.findAllComments();
        assertThat(comments).isNotNull().hasSize(EXPECTED_COMMENTS_COUNT)
                .allMatch(c -> !c.getText().equals(""));
    }

    @DisplayName("добавляет комментарий для книги в БД")
    @Test
    void shouldInsertComment() {
        String bookTitle = "ТестКнига0";
        Query queryBook = new Query();
        queryBook.addCriteria(Criteria.where("title").is(bookTitle));
        Book book = mongoTemplate.findOne(queryBook, Book.class);
        Comment comment = new Comment("Тест", book);
        commentRepository.save(comment);

        assertThat(comment.getId()).isNotEmpty();

        Query queryComment = new Query();
        queryComment.addCriteria(Criteria.where("_id").is(comment.getId()));
        Comment expectedComment = mongoTemplate.findOne(queryComment, Comment.class);

        assertThat(expectedComment).isNotNull().matches(c -> c.getText().equals(comment.getText()));
    }


    @DisplayName("удаляет комментарий из БД")
    @Test
    void shouldDeleteComment() {
        String bookTitle = "ТестКнига0";
        Query queryBook = new Query();
        queryBook.addCriteria(Criteria.where("title").is(bookTitle));
        Book book = mongoTemplate.findOne(queryBook, Book.class);
        assertThat(book).isNotNull();

        Query queryComment = new Query();
        queryBook.addCriteria(Criteria.where("bookId").is(book.getId()));
        Comment firstComment = mongoTemplate.findOne(queryComment, Comment.class);

        assertThat(firstComment).isNotNull();

        commentRepository.delete(firstComment);

        Query queryDeletedComment = new Query();
        queryDeletedComment.addCriteria(Criteria.where("_id").is(firstComment.getId()));
        Comment deletedComment = mongoTemplate.findOne(queryDeletedComment, Comment.class);

        assertThat(deletedComment).isNull();
    }

    @DisplayName("возвращает список всех комментариев по книге")
    @Test
    void shouldReturnExpectedBookCommentsList() {
        String bookTitle = "ТестКнига0";
        Query queryBook = new Query();
        queryBook.addCriteria(Criteria.where("title").is(bookTitle));
        Book book = mongoTemplate.findOne(queryBook, Book.class);
        assertThat(book).isNotNull();

        List<CommentEntry> books = commentRepository.findAllByBook_Id(book.getId());
        assertThat(books).isNotNull().hasSize(1)
                .allMatch(c -> !c.getText().equals(""));
    }
}