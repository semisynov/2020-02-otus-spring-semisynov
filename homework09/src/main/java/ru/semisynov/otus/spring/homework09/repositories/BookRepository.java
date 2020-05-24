package ru.semisynov.otus.spring.homework09.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.semisynov.otus.spring.homework09.dto.BookEntry;
import ru.semisynov.otus.spring.homework09.model.Author;
import ru.semisynov.otus.spring.homework09.model.Book;
import ru.semisynov.otus.spring.homework09.model.Genre;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b")
    List<BookEntry> findAllBooks();

    List<Book> findByAuthors(Author author);

    List<Book> findByGenres(Genre genre);
}