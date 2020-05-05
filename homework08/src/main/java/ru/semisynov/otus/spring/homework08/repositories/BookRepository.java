package ru.semisynov.otus.spring.homework08.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.semisynov.otus.spring.homework08.dto.BookEntry;
import ru.semisynov.otus.spring.homework08.model.Author;
import ru.semisynov.otus.spring.homework08.model.Book;
import ru.semisynov.otus.spring.homework08.model.Genre;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

    Optional<BookEntry> findBookById(String id);

    @Query("{}, {title: 1, authors: 1, genres: 1}")
    List<BookEntry> findAllBooks();

    List<Book> findByAuthors(Author author);

    List<Book> findByGenres(Genre genre);
}