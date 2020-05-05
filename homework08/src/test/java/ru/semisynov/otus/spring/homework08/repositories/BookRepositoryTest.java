package ru.semisynov.otus.spring.homework08.repositories;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.semisynov.otus.spring.homework08.config.ApplicationTestConfig;
import ru.semisynov.otus.spring.homework08.config.MongoConfig;
import ru.semisynov.otus.spring.homework08.dto.BookEntry;
import ru.semisynov.otus.spring.homework08.errors.ItemNotFoundException;
import ru.semisynov.otus.spring.homework08.model.Author;
import ru.semisynov.otus.spring.homework08.model.Book;
import ru.semisynov.otus.spring.homework08.model.Genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с книгами ")
@DataMongoTest
@Import({ApplicationTestConfig.class, MongoConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookRepositoryTest {

    private static final int EXPECTED_BOOKS_COUNT = 5;
    private static final String BOOK_TITLE_PREFIX = "ТестКнига";
    private static final String TEXT_NOT_FOUND = "Book not found";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("возвращает общее количество всех книг")
    @Test
    void shouldReturnExpectedBooksCount() {
        long count = bookRepository.count();
        assertThat(count).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("возвращает заданную книгу по её id")
    @Test
    void shouldReturnExpectedBookById() {
        List<Book> books = mongoTemplate.findAll(Book.class);
        Book book = books.get(0);

        Optional<Book> optionalBook = bookRepository.findById(book.getId());

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(book.getId()));
        Book expectedBook = mongoTemplate.findOne(query, Book.class);

        assertThat(optionalBook).isPresent().get().matches(b -> {
            assert expectedBook != null;
            return b.getTitle().equals(expectedBook.getTitle());
        });
    }

    @DisplayName("возвращает заданную книгу по её id dto")
    @Test
    void shouldReturnExpectedBookEntryById() {
        List<Book> books = mongoTemplate.findAll(Book.class);
        Book book = books.get(0);

        Optional<BookEntry> optionalBookEntry = bookRepository.findBookById(book.getId());

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(book.getId()));
        Book expectedBook = mongoTemplate.findOne(query, Book.class);

        assertThat(optionalBookEntry).isPresent().get().matches(b -> {
            assert expectedBook != null;
            return b.getTitle().equals(expectedBook.getTitle());
        });
    }

    @DisplayName("возвращает список всех книг")
    @Test
    void shouldReturnExpectedBooksList() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_BOOKS_COUNT)
                .allMatch(b -> !b.getTitle().equals(""));
    }

    @DisplayName("возвращает список всех книг dto")
    @Test
    void shouldReturnExpectedBooksEntryList() {
        List<BookEntry> books = bookRepository.findAllBooks();
        assertThat(books).isNotNull().hasSize(EXPECTED_BOOKS_COUNT)
                .allMatch(b -> !b.getTitle().equals(""));
    }

    @DisplayName("добавляет книгу в БД")
    @Test
    void shouldInsertBook() {
        List<Author> authors = Collections.emptyList();
        List<Genre> genres = Collections.emptyList();

        String testBookTitle = BOOK_TITLE_PREFIX + "10";
        Book testBook = new Book(testBookTitle, authors, genres);
        bookRepository.save(testBook);

        assertThat(testBook.getId()).isNotEmpty();

        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(testBookTitle));
        Book expectedBook = mongoTemplate.findOne(query, Book.class);

        assertThat(testBook).isEqualToComparingFieldByField(expectedBook);
        assertThat(expectedBook).isNotNull().matches(a -> !a.getTitle().isEmpty());
    }

    @DisplayName("возвращает заданную книгу по её названию")
    @Test
    void shouldReturnExpectedBookByTitle() {
        String bookTitle = BOOK_TITLE_PREFIX + "0";

        Optional<BookEntry> optionalBook = bookRepository.findBookByTitleIgnoreCase(bookTitle.toLowerCase());

        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(bookTitle));
        Book expectedBook = mongoTemplate.findOne(query, Book.class);

        assertThat(optionalBook).isPresent().get().matches(b -> {
            assert expectedBook != null;
            return b.getTitle().equals(expectedBook.getTitle());
        });
    }

    @DisplayName("удаляет книгу из БД")
    @Test
    void shouldDeleteBook() {
        String bookTitle = BOOK_TITLE_PREFIX + "0";

        Optional<BookEntry> optionalBook = bookRepository.findBookByTitleIgnoreCase(bookTitle.toLowerCase());
        String bookId = optionalBook.orElseThrow(() -> new ItemNotFoundException(TEXT_NOT_FOUND)).getId();

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(bookId));
        Book firstBook = mongoTemplate.findOne(query, Book.class);

        assertThat(firstBook).isNotNull();

        bookRepository.delete(firstBook);
        Book deletedBook = mongoTemplate.findOne(query, Book.class);

        assertThat(deletedBook).isNull();
    }

    @DisplayName("возвращает список всех книг по автору")
    @Test
    void shouldReturnExpectedBooksListByAuthor() {
        String authorName = "ТестАвтор0";
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(authorName));
        Author author = mongoTemplate.findOne(query, Author.class);

        List<Book> books = bookRepository.findByAuthors(author);
        assertThat(books).isNotNull().hasSize(1)
                .allMatch(b -> !b.getTitle().equals(""));
    }

    @DisplayName("возвращает список всех книг по жанру")
    @Test
    void shouldReturnExpectedBooksListByGenre() {
        String genreTitle = "ТестЖанр0";
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(genreTitle));
        Genre genre = mongoTemplate.findOne(query, Genre.class);

        List<Book> books = bookRepository.findByGenres(genre);
        assertThat(books).isNotNull().hasSize(1)
                .allMatch(b -> !b.getTitle().equals(""));
    }
}