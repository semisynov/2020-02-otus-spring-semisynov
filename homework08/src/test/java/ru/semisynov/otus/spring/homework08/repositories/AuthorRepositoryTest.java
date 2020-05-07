package ru.semisynov.otus.spring.homework08.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.semisynov.otus.spring.homework08.config.ApplicationTestConfig;
import ru.semisynov.otus.spring.homework08.config.MongoConfig;
import ru.semisynov.otus.spring.homework08.errors.DataReferenceException;
import ru.semisynov.otus.spring.homework08.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework08.model.Author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Repository для работы с авторами книг ")
@DataMongoTest
@Import({ApplicationTestConfig.class, MongoConfig.class})
@ComponentScan("ru.semisynov.otus.spring.homework08.events")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryTest {

    private static final int EXPECTED_AUTHORS_COUNT = 10;
    private static final String AUTHOR_NAME_PREFIX = "ТестАвтор";
    private static final String TEXT_NOT_FOUND = "Author not found";

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("возвращает общее количество всех авторов")
    @Test
    void shouldReturnExpectedAuthorsCount() {
        long count = authorRepository.count();
        assertThat(count).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("возвращает заданного автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        List<Author> authors = mongoTemplate.findAll(Author.class);
        Author author = authors.get(0);

        Optional<Author> optionalAuthor = authorRepository.findById(author.getId());

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(author.getId()));
        Author expectedAuthor = mongoTemplate.findOne(query, Author.class);

        assertThat(optionalAuthor).isPresent().get()
                .isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName("возвращает список всех авторов")
    @Test
    void shouldReturnExpectedAuthorsList() {
        List<Author> authors = authorRepository.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_AUTHORS_COUNT)
                .allMatch(a -> !a.getName().equals(""));
    }

    @DisplayName("добавляет автора в БД")
    @Test
    void shouldInsertAuthor() {
        String authorName = AUTHOR_NAME_PREFIX + "10";
        Author author = new Author(authorName);
        authorRepository.save(author);

        assertThat(author.getId()).isNotEmpty();

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(authorName));
        Author expectedAuthor = mongoTemplate.findOne(query, Author.class);

        assertThat(author).isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName("возвращает заданного автора по его имени")
    @Test
    void shouldReturnExpectedAuthorByName() {
        String authorName = AUTHOR_NAME_PREFIX + "1";

        Optional<Author> optionalAuthor = authorRepository.findByNameIgnoreCase(authorName.toLowerCase());

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(authorName));
        Author expectedAuthor = mongoTemplate.findOne(query, Author.class);

        assertThat(optionalAuthor).isPresent().get()
                .isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName("удаляет автора из БД")
    @Test
    void shouldDeleteAuthor() {
        String authorWithOutBook = AUTHOR_NAME_PREFIX + "9";

        Optional<Author> optionalAuthor = authorRepository.findByNameIgnoreCase(authorWithOutBook.toLowerCase());
        String authorId = optionalAuthor.orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND)).getId();

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(authorId));
        Author firstAuthor = mongoTemplate.findOne(query, Author.class);

        assertThat(firstAuthor).isNotNull();

        authorRepository.delete(firstAuthor);
        Author deletedAuthor = mongoTemplate.findOne(query, Author.class);

        assertThat(deletedAuthor).isNull();
    }

    @DisplayName("не удаляет автора из БД, есть связи")
    @Test
    void shouldNotDeleteAuthorWithBook() {
        String authorWithBookName = AUTHOR_NAME_PREFIX + "0";

        Optional<Author> optionalAuthor = authorRepository.findByNameIgnoreCase(authorWithBookName.toLowerCase());
        String authorId = optionalAuthor.orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND)).getId();

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(authorId));
        Author firstAuthor = mongoTemplate.findOne(query, Author.class);

        assertThat(firstAuthor).isNotNull();

        assertThrows(DataReferenceException.class, () -> authorRepository.delete(firstAuthor));
    }
}