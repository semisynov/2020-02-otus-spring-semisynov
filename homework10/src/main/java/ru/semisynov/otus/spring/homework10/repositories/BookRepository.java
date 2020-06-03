package ru.semisynov.otus.spring.homework10.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.semisynov.otus.spring.homework10.model.Author;
import ru.semisynov.otus.spring.homework10.model.Book;
import ru.semisynov.otus.spring.homework10.model.Genre;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthors(Author author);

    List<Book> findByGenres(Genre genre);
}