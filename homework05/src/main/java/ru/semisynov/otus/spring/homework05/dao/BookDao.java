package ru.semisynov.otus.spring.homework05.dao;

import ru.semisynov.otus.spring.homework05.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    long count();

    Optional<Book> getById(long id);

    List<Book> getAll();

    long insert(Book book);

    void deleteById(long id);
}