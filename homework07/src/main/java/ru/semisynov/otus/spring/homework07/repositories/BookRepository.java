package ru.semisynov.otus.spring.homework07.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.semisynov.otus.spring.homework07.dto.BookEntry;
import ru.semisynov.otus.spring.homework07.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<BookEntry> findBookById(long id);

    @Query("select b from Book b")
    List<BookEntry> findAllBooks();
}