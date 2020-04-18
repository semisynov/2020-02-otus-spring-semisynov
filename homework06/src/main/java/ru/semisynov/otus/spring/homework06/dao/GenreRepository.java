package ru.semisynov.otus.spring.homework06.dao;

import ru.semisynov.otus.spring.homework06.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    long count();

    Optional<Genre> findById(long id);

    List<Genre> findAll();

    Genre save(Genre genre);

    void deleteById(long id);

    Optional<Genre> getByTitle(String title);
}