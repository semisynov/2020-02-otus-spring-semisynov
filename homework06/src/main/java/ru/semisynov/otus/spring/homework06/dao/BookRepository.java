package ru.semisynov.otus.spring.homework06.dao;

import ru.semisynov.otus.spring.homework06.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    long count();

    Optional<Book> findById(long id);

    List<Book> findAll();

    Book save(Book book);

    void deleteById(long id);
}