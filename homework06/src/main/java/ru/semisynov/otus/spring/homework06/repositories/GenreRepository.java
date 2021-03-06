package ru.semisynov.otus.spring.homework06.repositories;

import ru.semisynov.otus.spring.homework06.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    long count();

    Optional<Genre> findById(long id);

    List<Genre> findAll();

    Genre save(Genre genre);

    void delete(Genre genre);

    Optional<Genre> getByTitle(String title);
}