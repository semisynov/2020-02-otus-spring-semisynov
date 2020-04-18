package ru.semisynov.otus.spring.homework06.repositories;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.semisynov.otus.spring.homework06.model.Book;
import ru.semisynov.otus.spring.homework06.model.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с комментариями для книг ")
@DataJpaTest
@Import(CommentRepositoryImpl.class)
class CommentRepositoryImplTest {

    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final int EXPECTED_COMMENTS_COUNT = 3;
    private static final long FIRST_ID = 1L;
    private static final String EXPECTED_TEXT = "AnyText1";
    private static final long FIRST_BOOK_ID = 1L;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("возвращает общее количество всех авторов")
    @Test
    void shouldReturnExpectedCommentsCount() {
        long count = commentRepository.count();
        assertThat(count).isEqualTo(EXPECTED_COMMENTS_COUNT);
    }

    @DisplayName("возвращает заданного комментарий по его id")
    @Test
    void shouldReturnExpectedCommentById() {
        Optional<Comment> optionalComment = commentRepository.findById(FIRST_ID);
        Comment expectedComment = entityManager.find(Comment.class, FIRST_ID);
        assertThat(optionalComment).isPresent().get()
                .isEqualToComparingFieldByField(expectedComment);
    }

    @DisplayName("возвращает список всех комментариев")
    @Test
    void shouldReturnExpectedCommentsList() {
        SessionFactory sessionFactory = entityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).isNotNull().hasSize(EXPECTED_COMMENTS_COUNT)
                .allMatch(a -> !a.getText().equals(""));

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("добавляет комментарий для книги в БД")
    @Test
    void shouldInsertComment() {
        Book testBook = entityManager.find(Book.class, FIRST_BOOK_ID);
        Comment testComment = new Comment(0L, LocalDateTime.now(), EXPECTED_TEXT, testBook);
        commentRepository.save(testComment);

        assertThat(testComment.getId()).isGreaterThan(0);

        Comment actualComment = entityManager.find(Comment.class, testComment.getId());
        assertThat(actualComment).isNotNull().matches(c -> c.getText().equals(EXPECTED_TEXT));
    }

    @DisplayName("удаляет комментарий из БД")
    @Test
    void shouldDeleteAuthor() {
        Comment firstComment = entityManager.find(Comment.class, FIRST_ID);
        assertThat(firstComment).isNotNull();
        entityManager.detach(firstComment);

        commentRepository.deleteById(FIRST_ID);
        Comment deletedComment = entityManager.find(Comment.class, FIRST_ID);

        assertThat(deletedComment).isNull();
    }

    @DisplayName("возвращает список всех комментариев по книге")
    @Test
    void shouldReturnExpectedBookCommentsList() {
        List<Comment> comments = commentRepository.findAllByBook(2L);
        assertThat(comments).isNotNull().hasSize(2)
                .allMatch(a -> !a.getText().equals(""));
    }
}