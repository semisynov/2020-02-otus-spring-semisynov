package ru.semisynov.otus.spring.homework06.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.semisynov.otus.spring.homework06.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework06.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework06.model.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Repository для работы с авторами книг ")
@DataJpaTest
@Import(AuthorRepositoryImpl.class)
class AuthorRepositoryImplTest {

    private static final int EXPECTED_AUTHORS_COUNT = 3;
    private static final String EXPECTED_AUTHOR_NAME_1 = "AnyAuthor1";
    private static final long EXPECTED_BOOK_ID = 3L;
    private static final int EXPECTED_BOOK_AUTHOR_COUNT = 2;
    private static final String TEXT_NOT_FOUND = "Author not found";

    private static final long FIRST_ID = 1L;

    private static final int EXPECTED_QUERIES_COUNT =  1;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("возвращает общее количество всех авторов")
    @Test
    void shouldReturnExpectedAuthorsCount() {
        long count = authorRepository.count();
        assertThat(count).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("возвращает заданного автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Optional<Author> optionalAuthor = authorRepository.findById(1L);
        Author expectedAuthor = entityManager.find(Author.class, FIRST_ID);
        assertThat(optionalAuthor).isPresent().get()
                .isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName("возвращает список всех авторов")
    @Test
    void shouldReturnExpectedAuthorsList() {
        SessionFactory sessionFactory = entityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        List<Author> authors = authorRepository.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_AUTHORS_COUNT)
                .allMatch(a -> !a.getName().equals(""));

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("добавляет автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author testAuthor = new Author(0L, "TestAuthor");
        authorRepository.save(testAuthor);

        assertThat(testAuthor.getId()).isGreaterThan(0);

        Author actualAuthor = entityManager.find(Author.class, testAuthor.getId());
        assertThat(actualAuthor).isNotNull().matches(a -> !a.getName().isEmpty());
    }

    @DisplayName("удаляет автора из БД")
    @Test
    void shouldDeleteAuthor() {
        Author firstAuthor = entityManager.find(Author.class, FIRST_ID);
        assertThat(firstAuthor).isNotNull();
        entityManager.detach(firstAuthor);

        authorRepository.deleteById(FIRST_ID);
        Author deletedAuthor = entityManager.find(Author.class, FIRST_ID);

        assertThat(deletedAuthor).isNull();
    }

    @DisplayName("не удаляет автора из БД есть связь")
    @Test
    void shouldNotDeleteAuthor() {
        assertThrows(DataReferenceException.class, () -> authorRepository.deleteById(2L));
    }

    @DisplayName("возвращает заданного автора по его имени")
    @Test
    void shouldReturnExpectedAuthorByName() {
        Author author = authorRepository.findByName(EXPECTED_AUTHOR_NAME_1).orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND));
        assertEquals(author.getName(), EXPECTED_AUTHOR_NAME_1);
    }
}