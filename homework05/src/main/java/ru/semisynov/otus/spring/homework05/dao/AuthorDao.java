package ru.semisynov.otus.spring.homework05.dao;

import ru.semisynov.otus.spring.homework05.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    long count();

    Optional<Author> getById(long id);

    List<Author> getAll();

    long insert(Author author);

    void deleteById(long id);

    List<Author> getByBookId(long id);
}
