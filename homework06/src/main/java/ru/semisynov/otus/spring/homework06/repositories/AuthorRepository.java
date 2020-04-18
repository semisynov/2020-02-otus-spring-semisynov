package ru.semisynov.otus.spring.homework06.repositories;

import ru.semisynov.otus.spring.homework06.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    long count();

    Optional<Author> findById(long id);

    List<Author> findAll();

    Author save(Author author);

    void deleteById(long id);

    Optional<Author> findByName(String name);
}