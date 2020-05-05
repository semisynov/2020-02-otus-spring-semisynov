package ru.semisynov.otus.spring.homework08.repositories;

import java.util.List;
import java.util.Optional;

import ru.semisynov.otus.spring.homework08.dto.BookEntry;
import ru.semisynov.otus.spring.homework08.model.Author;
import ru.semisynov.otus.spring.homework08.model.Book;
import ru.semisynov.otus.spring.homework08.model.Genre;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookRepository extends MongoRepository<Book, String> {

    Optional<BookEntry> findBookById(String id);

    Optional<BookEntry> findBookByTitleIgnoreCase(String title);

    @Query("{}, {title: 1, authors: 1, genres: 1}")
    List<BookEntry> findAllBooks();

    List<Book> findByAuthors(Author author);

    List<Book> findByGenres(Genre genre);
}